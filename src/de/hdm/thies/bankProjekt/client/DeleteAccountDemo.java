package de.hdm.thies.bankProjekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Löschen des Kontos mit der
 * höchsten Kontonummer von Kunde Nr. 1.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * {@link CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies
 * @version 1.0
 */
public class DeleteAccountDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Delete Account";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Löschen des Kontos mit der höchsten Kontonummer von Kunde Nr. 1.");

    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    bankVerwaltung.getCustomerById(1, new UseCustomerForDeletion(this));
  }

  /**
   * <p>
   * Wir nutzen eine Nested Class, um den zurückerhaltenen Kunden weiter zu
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
  class UseCustomerForDeletion implements AsyncCallback<Customer> {
    private Showcase showcase = null;

    public UseCustomerForDeletion(Showcase c) {
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
        bankVerwaltung.getAccountsOf(customer,
            new GetAllAccountsOfCustomerCallback(this.showcase));
      }
    }

  }

  /**
   * Analog zu {@link UseCustomerForDeletion}.
   * 
   * @see UseCustomerForDeletion
   * @author thies
   * @version 1.0
   * 
   */
  class GetAllAccountsOfCustomerCallback
      implements AsyncCallback<Vector<Account>> {
    private Showcase showcase = null;

    public GetAllAccountsOfCustomerCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Vector<Account> accounts) {
      if (accounts != null) {
        String message = "Der Kunde besitzt " + accounts.size();

        if (accounts.size() == 1)
          message += " Konto.";
        else
          message += " Konten.";

        this.showcase.append(message);

        // Wenn der Kunde mind. ein Konto bei uns führt, dann ...
        if (accounts.size() > 0) {
          // Nimm das Konto, das in accounts an hinterster Position steht
          Account a = accounts.elementAt(accounts.size() - 1);

          // Zeige Nummer und Stand dieses Kontos noch einmal an
          this.showcase.append("Lösche Account: " + a.getId());

          // Bitte die Verwaltung, das Konto zu löschen
          BankAdministrationAsync bankVerwaltung = ClientsideSettings
              .getBankVerwaltung();
          bankVerwaltung.delete(a, new DeleteAccountCallback(this.showcase));
        }
      }
    }

  }

  /**
   * Analog zu <code>UseCustomerForDeletion</code>.
   * 
   * @see UseCustomerForDeletion
   * @author thies
   * @version 1.0
   * 
   */
  class DeleteAccountCallback implements AsyncCallback<Void> {
    private Showcase showcase = null;

    public DeleteAccountCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
    public void onSuccess(Void result) {
      this.showcase.append("Konto erfolgreich gelöscht");
    }
  }
}
