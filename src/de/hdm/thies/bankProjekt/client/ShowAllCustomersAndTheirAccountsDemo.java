package de.hdm.thies.bankProjekt.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * <p>
 * Ein weiterer Showcase. Dieser demonstriert das Anzeigen sämtlicher Kunden
 * sowie deren Konten und deren Kontostände.
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
public class ShowAllCustomersAndTheirAccountsDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Show all customers and their accounts";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    this.append("Lade alle Kunden...");

    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    bankVerwaltung.getAllCustomers(new GetAllCustomersCallback(this));
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
   * Weitere Dokumentation siehe {@link CreateAccountDemo.UseCustomer}.
   * </p>
   * 
   * @see CreateAccountDemo.UseCustomer
   */
  class GetAllCustomersCallback implements AsyncCallback<Vector<Customer>> {
    private Showcase showcase = null;

    public GetAllCustomersCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Vector<Customer> customers) {
      if (customers != null) {
        BankAdministrationAsync bankVerwaltung = ClientsideSettings
            .getBankVerwaltung();

        for (Customer c : customers) {
          this.showcase.append("Fordere alle Konten für Kunde "
              + c.getFirstName() + " " + c.getLastName() + " an.");

          bankVerwaltung.getAccountsOf(c, new GetAccountsOfCustomerCallback(
              this.showcase, c));
        }
      }
    }

  }

  /**
   * <p>
   * Wir nutzen auch hier eine Nested Class, um die zurückerhaltenen Objekte
   * weiter zu bearbeiten.
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
   * Weitere Dokumentation siehe {@link CreateAccountDemo.UseCustomer}.
   * </p>
   * 
   * @see CreateAccountDemo.UseCustomer
   */
  class GetAccountsOfCustomerCallback implements AsyncCallback<Vector<Account>> {
    private Showcase showcase = null;

    /**
     * Mit diesem Attribut merken wir uns, bzgl. welches Kunden die
     * Ergebnissmenge erwartet wird. Wir bauen auf diese Weise einen Kontext für
     * asynchrone Aufrufe bzw. Antworten auf.
     */
    private Customer customer = null;

    public GetAccountsOfCustomerCallback(Showcase c, Customer customer) {
      this.showcase = c;
      this.customer = customer;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Vector<Account> accounts) {
      if (accounts != null) {
        if (accounts.size() != 0) {
          StringBuffer ktoNummern = new StringBuffer();

          for (Account a : accounts) {
            ktoNummern.append("#" + a.getId() + " ");

            BankAdministrationAsync bankVerwaltung = ClientsideSettings
                .getBankVerwaltung();

            bankVerwaltung.getBalanceOf(a, new GetBalanceOfAccountCallback(
                this.showcase, a));
          }

          this.showcase
              .append("Erhalte für Kunde " + this.customer.getFirstName() + " "
                  + this.customer.getLastName() + " folgende Konten: "
                  + ktoNummern);

        }
        else {
          this.showcase.append("Der Kunde " + this.customer.getFirstName()
              + " " + this.customer.getLastName() + " besitzt keine Konten.");
        }
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
     * (Instance of GetAccountsOfCustomerCallback
     * 
     *    Hier sind sämtliche Infos zum Kontext nach dem ersten Call bzgl. 
     *    des Kunden verfügbar, also z.B. das Kundenobjekt und als Ergebnis des
     *    Call auch dessen Konten.
     *    
     *    (Instance of GetBalanceOfAccountCallback
     *    
     *       Hier sind zusätzlich noch die Infos zum Kontext nach dem zweiten 
     *       Call, also z.B. der Kontostand des jeweiligen Kontos, verfügbar.
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
    class GetBalanceOfAccountCallback implements AsyncCallback<Float> {
      private Showcase showcase = null;
      private Account account = null;

      public GetBalanceOfAccountCallback(Showcase c, Account account) {
        this.showcase = c;
        this.account = account;
      }

      @Override
      public void onFailure(Throwable caught) {
        this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
      }

      @Override
	public void onSuccess(Float balance) {
        if (balance != null) {
          this.showcase.append("Konto #" + this.account.getId() + ", Stand: "
              + balance.floatValue() + ", Inhaber: " + customer.getFirstName()
              + " " + customer.getLastName());
        }
      }

    }

  }

}
