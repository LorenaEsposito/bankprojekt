package de.hdm.thies.bankProjekt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Löschen des Kunden mit der
 * Kundennummer 11.
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
public class DeleteCustomerDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Delete Customer";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Löschen des Kunden mit der Kundennummer 11.");

    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    bankVerwaltung.getCustomerById(11, new DeleteCustomerCallback(this));
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
  class DeleteCustomerCallback implements AsyncCallback<Customer> {
    private Showcase showcase = null;

    public DeleteCustomerCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Customer customer) {
      if (customer != null) {
        BankAdministrationAsync bankVerwaltung = ClientsideSettings
            .getBankVerwaltung();
        this.showcase.append("Lösche Kunde " + customer.getFirstName() + " "
            + customer.getLastName());
        bankVerwaltung.delete(customer, new CustomerDeletedCallback(
            this.showcase));
      }
    }

  }

  /**
   * Analog zu {@link DeleteCustomerCallback}.
   * 
   * @see DeleteCustomerCallback
   * @author thies
   * @version 1.0
   * 
   */
 class CustomerDeletedCallback implements AsyncCallback<Void> {
    private Showcase showcase = null;

    public CustomerDeletedCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Void result) {
      this.showcase.append("Kunde erfolgreich gelöscht");
    }

  }
}
