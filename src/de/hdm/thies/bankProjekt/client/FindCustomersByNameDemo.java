package de.hdm.thies.bankProjekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.Customer;

/**
 * <p>
 * Ein weiterer Showcase. Dieser sucht nach allen Kunden, deren Nachname 'Kohl'
 * ist und gibt diese aus.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in {@link
 * CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies
 * @version 1.0
 * 
 */
public class FindCustomersByNameDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Find Customers by Name";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Wir suchen alle Kunden, deren Nachname 'Kohl' ist und geben diese aus.");

    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    // Die BankAdministration fragen wir, ob sie Kunden namens Kohl kennt.
    bankVerwaltung.getCustomerByName("Kohl", new KundenAusgebenCallback(this));
  }

  /**
   * <p>
   * Wir nutzen eine Nested Class, um die zurückerhaltenen Objekte weiter zu
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
  class KundenAusgebenCallback implements AsyncCallback<Vector<Customer>> {
    private Showcase showcase = null;

    public KundenAusgebenCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
    public void onSuccess(Vector<Customer> result) {
      if (result != null) {
        for (Customer c : result) {
          if (c != null) {
            // Kundennummer und Name ausgeben
            this.showcase.append("Kunde #" + c.getId() + ": " + c.getLastName()
                + ", " + c.getFirstName());
          }
        }

        if (result.size() == 1)
          this.showcase.append("1 Element erhalten");
        else
          this.showcase.append(result.size() + " Elemente erhalten");

      }
      else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }

  }
}
