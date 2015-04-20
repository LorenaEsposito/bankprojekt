package de.hdm.thies.bankProjekt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.BankAdministrationAsync;
import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * In diesem Showcase wird ein neues Konto für den Kunden mit der Kundennummer 1
 * erstellt.
 * 
 * @author thies
 * @version 1.0
 * 
 */
public class CreateAccountDemo extends Showcase {

  /**
   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
   * Methode zu erstellen ist.
   * 
   * @see Showcase#getHeadlineText()
   */
  @Override
  protected String getHeadlineText() {
    return "Konto anlegen";
  }

  /**
   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
   * eine "Einschubmethode", die von einer Methode der Basisklasse
   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
   */
  @Override
  protected void run() {
    // Ankündigung, was nun geschehen wird.
    this.append("Anlegen eines neuen Kontos für den Kunden mit Kd.-Nr. 1.");

    // Erfragen der BankAdministration.
    BankAdministrationAsync bankVerwaltung = ClientsideSettings.getBankVerwaltung();

    /*
     * Auslesen des Kunden-Objekts, das mit der Kundenummer 1 identifiziert
     * wird. Gemäß GWT-RPC wird ein Callback (bedenken Sie, dass sämtliche
     * Funktionsaufrufe mittels GWT-RPC asynchron erfolgen) für die Bearbeitung
     * des Rückgabewerts benötigt.
     */
    bankVerwaltung.getCustomerById(1, new UseCustomer(this));

  }

  /**
   * <p>
   * Wir nutzen eine Nested Class, um den zurückerhaltenen Kunden weiter zu
   * bearbeiten.
   * </p>
   * <p>
   * <b>Amerkungen:</b> Eine Nested Class besitzt den Vorteil, die Lokalität des
   * Gesamtsystems zu fördern, da der Klassenname (hier: "UseCustomer")
   * außerhalb von CreateAccountDemo nicht "verbraucht" wird. Doch Vorsicht!
   * Wenn eine Klasse mehrfach, also gewissermaßen an mehreren Stellen im
   * Programm, nutzbar ist, sollte man überlegen, ob man eine solche Klasse als
   * normale - also separate - Klasse realisiert bzw. anordnet.
   * </p>
   */
  class UseCustomer implements AsyncCallback<Customer> {
    /**
     * Wir merken uns den Showcase, an den wir berichten. An ihn senden wir
     * jeglichen Output.
     */
    private Showcase showcase = null;

    /**
     * Konstruktor, der eine Verbindung zu einem Showcase fordert.
     * 
     * @param c der Showcase, an den wir unseren Output senden
     */
    public UseCustomer(Showcase c) {
      this.showcase = c;
    }

    /**
     * Diese Methode wird durch die GWT-RPC Runtime aufgerufen, wenn es zu einem
     * Problem während des Aufrufs oder der Server-seitigen Abbarbeitung kam.
     */
    @Override
    public void onFailure(Throwable caught) {
      /*
       * Wenn etwas schief geht, dann melden wir das an den Showcase und lassen
       * es auf sich beruhen. In einem Produktivsystem würde man hier ein
       * Recovery der Problemsituation steuern.
       */
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    /**
     * <code>onSuccess(...)</code> wird durch die GWT-RPC Runtime aufgerufen,
     * wenn wie erwartet das Ergebnis des Funktionsaufrufs vom Server an den
     * Client geliefert wird.
     */
    @Override
	public void onSuccess(Customer customer) {
      /*
       * Immer brav abfragen, ob wir einen brauchbaren Wert zurückerhalten
       * haben, bevor wir diesen benutzen. Dieses Vorgehen ist ein Aspekt der
       * defensiven Programmierung und erspart Ihnen so manche
       * NullPointerException!
       */
      if (customer != null) {
        /*
         * Wenn wir nun einen Kunden vorliegen haben, werden wir in diesem
         * Beispiel nun die BankAdministration bitten, uns für diesen Kunden ein
         * neues Konto anzulegen und dieses uns über einen weiteren Callback
         * zurückzuliefern.
         */
        BankAdministrationAsync bankVerwaltung = ClientsideSettings
            .getBankVerwaltung();
        bankVerwaltung.createAccountFor(customer, new CreateAccountCallback(
            this.showcase));
      }
    }

  }

  /**
   * <p>
   * Ein weiterer Callback, ebenfalls als Nested Class realisiert. Da die
   * Vorgehensweise hier die gleiche ist wie bei <code>UseCustomer</code>, sei
   * auf deren Dokumentation verwiesen.
   * </p>
   * <p>
   * <b>Hinweis:</b> Beachten Sie, wie man durch fortgesetzte Funktionsaufrufe
   * eine Callback-Klasse nach der anderen erstellen muss, um den jeweiligen
   * Kontext der Rückgabe eines Ergebnisses und dessen kontextsensitiven
   * Weiterbearbeitung systematisch umzusetzen. Auf den ersten Blick mag Ihnen
   * dies umständlich vorkommen. Überlegen Sie ich jedoch auch, dass wir somit
   * sehr schön die Möglichkeit bekommen, eine strukturierte Fehlerbehandlung zu
   * implementieren!
   * </p>
   * 
   * @author thies
   * @version 1.0
   * @see UseCustomer
   */
  class CreateAccountCallback implements AsyncCallback<Account> {
    private Showcase showcase = null;

    public CreateAccountCallback(Showcase c) {
      this.showcase = c;
    }

    @Override
    public void onFailure(Throwable caught) {
      this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
    }

    @Override
	public void onSuccess(Account account) {
      if (account != null) {
        this.showcase.append("Konto mit Kto.-Nr. " + account.getId()
            + " wurde angelegt.");
      }
    }
  }

}
