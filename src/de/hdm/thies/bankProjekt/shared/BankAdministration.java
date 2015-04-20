package de.hdm.thies.bankProjekt.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Banken.
 * </p>
 * <p>
 * <b>Frage:</b> Warum werden diese Methoden nicht als Teil der Klassen
 * {@link Bank}, {@link Customer}, {@link Account} oder {@link Transaction}
 * implementiert?<br>
 * <b>Antwort:</b> Z.B. das Löschen eines Kunden erfordert Kenntnisse über die
 * Verflechtung eines Kunden mit Konto-Objekten. Um die Klasse <code>Bank</code>
 * bzw. <code>Customer</code> nicht zu stark an andere Klassen zu koppeln, wird
 * das Wissen darüber, wie einzelne "Daten"-Objekte koexistieren, in der
 * vorliegenden Klasse gekapselt.
 * </p>
 * <p>
 * Natürlich existieren Frameworks wie etwa Hibernate, die dies auf eine andere
 * Weise realisieren. Wir haben jedoch ganz bewusst auf deren Nutzung
 * verzichtet, um in diesem kleinen Demoprojekt den Blick auf das Wesentliche
 * nicht unnötig zu verstellen.
 * </p>
 * <p>
 * <code>@RemoteServiceRelativePath("bankadministration")</code> ist bei der
 * Adressierung des aus der zugehörigen Impl-Klasse entstehenden
 * Servlet-Kompilats behilflich. Es gibt im Wesentlichen einen Teil der URL des
 * Servlets an.
 * </p>
 * 
 * @author Thies
 */
@RemoteServiceRelativePath("bankadministration")
public interface BankAdministration extends RemoteService {

  /**
   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
   * {@link BankVerwaltungImpl} notwendig. Bitte diese Methode direkt nach der
   * Instantiierung aufrufen.
   * 
   * @throws IllegalArgumentException
   */
  public void init() throws IllegalArgumentException;

  /**
   * Einen Kunden anlegen.
   * 
   * @param first Vorname
   * @param last Nachname
   * @return Ein fertiges Kunden-Objekt.
   * @throws IllegalArgumentException
   */
  public Customer createCustomer(String first, String last)
      throws IllegalArgumentException;

  /**
   * Ein neues Konto für einen gegebenen Kunden eröffnen.
   * 
   * @param c der Kunde
   * @return fertiges Konto-Objekt
   * @throws IllegalArgumentException
   */
  public Account createAccountFor(Customer c) throws IllegalArgumentException;

  /**
   * Eine neue Buchung für ein gegebenes Konto erstellen.
   * 
   * @param source das Quellkonto
   * @param target das Zielkonto
   * @param value der zu buchende Betrag
   * @return fertiges Transaction-Objekt
   * @throws IllegalArgumentException
   */
  public Transaction createTransactionFor(Account source, Account target,
      float value) throws IllegalArgumentException;

  /**
   * Hier wird der Use Case abgebildet, dass der Kunde eine Barabhebung von
   * einem Konto machen möchte. Es wird hierdurch ein Buchungssatz
   * 
   * <pre>
   * Kundenkonto <Betrag>
   * an Kasse <Betrag>
   * </pre>
   * 
   * realisiert.
   * 
   * @param customerAccount das Kundenkonto, von dem die Barabhebung erfolgen
   *          soll.
   * @param amount der Betrag, der abgehoben werden soll.
   * @return Ein <code>Transaction</code>-Objekt, das den resultierenden
   *         Buchungssatz darstellt.
   */
  public Transaction createWithdrawal(Account customerAccount, float amount);
  
  /**
   * Hier wird der Use Case abgebildet, dass der Kunde eine Bareinzahlung auf
   * einem Konto machen möchte. Es wird hierdurch ein Buchungssatz
   * 
   * <pre>
   * Kasse <Betrag>
   * an Kundenkonto <Betrag>
   * </pre>
   * 
   * realisiert.
   * 
   * @param customerAccount das Kundenkonto, auf das der Betrag eingezahlt
   *          werden soll.
   * @param amount der Betrag, der eingezahlt wird.
   * @return Ein <code>Transaction</code>-Objekt, das den resultierenden
   *         Buchungssatz darstellt.
   */
  public Transaction createDeposit(Account customerAccount, float amount);
  
  /**
   * <p>
   * Auslesen sämtlicher mit diesem Konto in Verbindung stehenden
   * Soll-Buchungen. Diese Methode wird in {@link #getBalanceOf(Account)}
   * verwendet.
   * </p>
   * 
   * @param k das Konto, dessen Soll-Buchungen wir bekommen wollen.
   * @return eine Liste aller Soll-Buchungen
   * @throws IllegalArgumentException
   */
  public ArrayList<Transaction> getDebitsOf(Account k)
      throws IllegalArgumentException;

  /**
   * <p>
   * Auslesen sämtlicher mit diesem Konto in Verbindung stehenden
   * Haben-Buchungen. Diese Methode wird in {@link #getBalanceOf(Account)}
   * verwendet.
   * </p>
   * 
   * @param k das Konto, dessen Haben-Buchungen wir bekommen wollen.
   * @return eine Liste aller Haben-Buchungen
   * @throws IllegalArgumentException
   */
  public ArrayList<Transaction> getCreditsOf(Account k)
      throws IllegalArgumentException;

  /**
   * Auslesen der zugeordneten Bank.
   * 
   * @return Bank-Objekt
   * @throws IllegalArgumentException
   */
  public Bank getBank() throws IllegalArgumentException;

  /**
   * Setzen der zugeordneten Bank.
   * 
   * @para Bank-Objekt
   * @throws IllegalArgumentException
   */
  public void setBank(Bank b) throws IllegalArgumentException;

  /**
   * Alle Konten eines Kunden auslesen.
   * 
   * @param Kundenobjekt
   * @return Vector-Objekt mit Account-Objekten bzgl. des Kunden.
   * @throws IllegalArgumentException
   */
  public Vector<Account> getAccountsOf(Customer c)
      throws IllegalArgumentException;

  /**
   * Suchen eines Account-Objekts, dessen Kontonummer bekannt ist.
   * 
   * @param id ist die Kontonummer.
   * @return Das erste Konto-Objekt, dass den Suchkriterien entspricht.
   * @throws IllegalArgumentException
   */
  public Account getAccountById(int id) throws IllegalArgumentException;

  /**
   * Suchen von Customer-Objekten, von denen der Nachname bekannt ist.
   * 
   * @param lastName ist der Nachname.
   * @return Alle Customer-Objekte, die die Suchkriterien erfüllen.
   * @throws IllegalArgumentException
   */
  public Vector<Customer> getCustomerByName(String lastName)
      throws IllegalArgumentException;

  /**
   * Suchen eines Customer-Objekts, dessen Kundennummer bekannt ist.
   * 
   * @param id ist die Kundennummer.
   * @return Das erste Customer-Objekt, dass den Suchkriterien entspricht.
   * @throws IllegalArgumentException
   */
  public Customer getCustomerById(int id) throws IllegalArgumentException;

  /**
   * Sämtliche Kunden der Bank auslesen.
   * 
   * @return Vector s�mtlicher Kunden
   */
  public Vector<Customer> getAllCustomers() throws IllegalArgumentException;

  /**
   * Sämtliche Konten der Bank auslesen.
   * 
   * @return Vector sämtlicher Konten
   */
  public Vector<Account> getAllAccounts() throws IllegalArgumentException;

  /**
   * Speichern eines Account-Objekts in der Datenbank.
   * 
   * @param a zu sicherndes Objekt.
   * @throws IllegalArgumentException
   */
  public void save(Account a) throws IllegalArgumentException;

  /**
   * Speichern eines Customer-Objekts in der Datenbank.
   * 
   * @param c zu sicherndes Objekt.
   * @throws IllegalArgumentException
   */
  public void save(Customer c) throws IllegalArgumentException;

  /**
   * Auslesen des Kontostands des übergebenen Kontos.
   * 
   * @param k das Konto, dessen Kontostand wird auslesen möchten.
   * @return Kontostand als <code>float</code>
   * @throws IllegalArgumentException
   */
  public float getBalanceOf(Account k) throws IllegalArgumentException;

  /**
   * Löschen des übergebenen Kontos.
   * 
   * @param a das zu löschende Konto
   * @throws IllegalArgumentException
   */
  public void delete(Account a) throws IllegalArgumentException;

  /**
   * Löschen des übergebenen Kunden.
   * 
   * @param c der zu löschende Kunde
   * @throws IllegalArgumentException
   */
  public void delete(Customer c) throws IllegalArgumentException;

  /**
   * Löschen der übergebenen Buchung.
   * 
   * @param t die zu löschende Buchung
   * @throws IllegalArgumentException
   */
  public void delete(Transaction t) throws IllegalArgumentException;

}
