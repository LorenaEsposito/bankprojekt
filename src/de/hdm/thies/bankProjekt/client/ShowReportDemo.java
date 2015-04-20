package de.hdm.thies.bankProjekt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.*;
import de.hdm.thies.bankProjekt.shared.bo.*;
import de.hdm.thies.bankProjekt.shared.report.*;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Anzeigen eines Reports zum
 * Kunden mit der Kundennummer 1. Demonstration der Nutzung des Report
 * Generators.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * {@link CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies
 * @version 1.0
 * 
 */
public class ShowReportDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Show Report";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Auslesen des Kunden mit Kd.-Nr. 1.");

    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    bankVerwaltung.getCustomerById(1, new GetCustomerCallback(this));
  }

  /**
   * <p>
   * Wir nutzen eine Nested Class, um das zurückerhaltene Objekt weiter zu
   * bearbeiten.
   * </p>
   * <p>
   * <b>Amerkungen:</b> Eine Nested Class besitzt den Vorteil, die Lokalität des
   * Gesamtsystems zu fördern, da der Klassenname (hier: "UseCustomer")
   * außerhalb von DeleteAccountDemo nicht "verbraucht" wird. Doch Vorsicht!
   * Wenn eine Klasse mehrfach, also gewissermaßen an mehreren Stellen im
   * Programm, nutzbar ist, sollte man überlegen, ob man eine solche Klasse als
   * normale - also separate - Klasse realisiert bzw. anordnet.
   * </p>
   * <p>
   * Weitere Dokumentation siehe <code>CreateAccountDemo.UseCustomer</code>.
   * </p>
   * 
   * @see CreateAccountDemo.UseCustomer
   */
  class GetCustomerCallback implements AsyncCallback<Customer> {
    private Showcase showcase = null;

    public GetCustomerCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Customer customer) {
      if (customer != null) {
        ReportGeneratorAsync reportGenerator = ClientsideSettings
            .getReportGenerator();

        reportGenerator.createAllAccountsOfCustomerReport(customer,
            new AllAccountsOfCustomerReportCallback(this.showcase));
      }
    }

    /**
     * <p>
     * Diese Klasse ist eine Nested Classs innerhalb einer Nested Class! Auf
     * diese Weise können wir einen klassenbezogenen Verarbeitungskontext
     * aufbauen, also gewissermaßen einen klassenbasierter Stack.
     * </p>
     * <p>
     * <b>Erläuterung:</b> Stellen Sie sich folgende Struktur vor (Syntax frei
     * erfunden):
     * 
     * <pre>
     * (Instance of GetCustomerCallback
     * 
     *    Hier sind sämtliche Infos zum Kontext nach dem ersten Call bzgl. 
     *    des Kunden verfügbar, also als Ergebnis des Calls das Kundenobjekt.
     *    
     *    (Instance of AllAccountsOfCustomerReportCallback
     *    
     *       Hier sind zusätzlich noch die Infos zum Kontext nach dem zweiten 
     *       Call, also der fertige Report zu dessen Weiterverarbeitung, verfügbar.
     *    
     *    )
     * )
     * </pre>
     * 
     * </p>
     * 
     * @author thies
     * @version 1.0
     * 
     */
    class AllAccountsOfCustomerReportCallback
        implements AsyncCallback<AllAccountsOfCustomerReport> {
      private Showcase showcase = null;

      public AllAccountsOfCustomerReportCallback(Showcase c) {
        this.showcase = c;
      }

      @Override
      public void onFailure(Throwable caught) {
        this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
      }

      @Override
      public void onSuccess(AllAccountsOfCustomerReport report) {
        if (report != null) {
          HTMLReportWriter writer = new HTMLReportWriter();
          writer.process(report);
          this.showcase.append(writer.getReportText());
        }
      }
    }
  }

}
