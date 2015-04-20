package de.hdm.thies.bankProjekt.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Transation</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see CustomerMapper, AccountMapper
 * @author Thies
 */
public class TransactionMapper {

  /**
   * Die Klasse TransactionMapper wird nur einmal instantiiert. Man spricht
   * hierbei von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   * @see transactionMapper()
   */
  private static TransactionMapper transactionMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen. 
   */
  protected TransactionMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>TransactionMapper.transactionMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
   * Instanz von <code>TransactionMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> TransactionMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>TransactionMapper</code>-Objekt.
   * @see transactionMapper
   */
  public static TransactionMapper transactionMapper() {
    if (transactionMapper == null) {
      transactionMapper = new TransactionMapper();
    }

    return transactionMapper;
  }

  /**
   * Suchen einer Buchung mit vorgegebener Buchungsnummer. Da diese eindeutig
   * ist, wird genau ein Objekt zur�ckgegeben.
   * 
   * @param id Primärschlüsselattribut (->DB)
   * @return Transaction-Objekt, das dem übergebenen Schlüssel entspricht, null
   *         bei nicht vorhandenem DB-Tupel.
   */
  public Transaction findByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs = stmt
          .executeQuery("SELECT id, sourceAccount, targetAccount, amount FROM transactions "
              + "WHERE id=" + id + " ORDER BY sourceAccount");

      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
       * werden. Prüfe, ob ein Ergebnis vorliegt.
       */
      if (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setSourceAccountID(rs.getInt("sourceAccount"));
        t.setTargetAccountID(rs.getInt("targetAccount"));
        t.setAmount(rs.getFloat("amount"));
        return t;
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Buchungen.
   * 
   * @return Ein Vektor mit Transaction-Objekten, die sämtliche Buchungen
   *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
   *         oder ggf. auch leerer Vetor zurückgeliefert.
   */
  public Vector<Transaction> findAll() {
    Connection con = DBConnection.connection();

    // Ergebnisvektor vorbereiten
    Vector<Transaction> result = new Vector<Transaction>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, sourceAccount, targetAccount, amount FROM transactions "
              + " ORDER BY sourceAccount");

      // Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
      while (rs.next()) {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setSourceAccountID(rs.getInt("sourceAccount"));
        t.setTargetAccountID(rs.getInt("targetAccount"));
        t.setAmount(rs.getFloat("amount"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(t);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurückgeben
    return result;
  }

  /**
   * Auslesen aller Ab-Buchungen eines durch Fremdschlüssel (Kontonr.) gegebenen
   * Kontos.
   * 
   * @see findByOwner(Customer owner)
   * @param accountID Schlüssel des zugehörigen Kontos.
   * @return Ein Vektor mit Transaction-Objekten, die sämtliche Buchungen des
   *         betreffenden Kontos repräsentieren. Bei evtl. Exceptions wird ein
   *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
   */
  public Vector<Transaction> findBySourceAccount(int accountID) {
    Connection con = DBConnection.connection();
    Vector<Transaction> result = new Vector<Transaction>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, sourceAccount, targetAccount, amount FROM transactions "
              + "WHERE sourceAccount=" + accountID + " ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
      while (rs.next()) {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setSourceAccountID(rs.getInt("sourceAccount"));
        t.setTargetAccountID(rs.getInt("targetAccount"));
        t.setAmount(rs.getFloat("amount"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(t);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurückgeben
    return result;
  }

  /**
   * Auslesen aller Zu-Buchungen eines durch Fremdschlüssel (Kontonr.) gegebenen
   * Kontos.
   * 
   * @see findByOwner(Customer owner)
   * @param accountID Schlüssel des zugehörigen Kontos.
   * @return Ein Vektor mit Transaction-Objekten, die sämtliche Buchungen des
   *         betreffenden Kontos repräsentieren. Bei evtl. Exceptions wird ein
   *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
   */
  public Vector<Transaction> findByTargetAccount(int accountID) {
    Connection con = DBConnection.connection();
    Vector<Transaction> result = new Vector<Transaction>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT id, sourceAccount, targetAccount, amount FROM transactions "
              + "WHERE targetAccount=" + accountID + " ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
      while (rs.next()) {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setSourceAccountID(rs.getInt("sourceAccount"));
        t.setTargetAccountID(rs.getInt("targetAccount"));
        t.setAmount(rs.getFloat("amount"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(t);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurückgeben
    return result;
  }

  /**
   * Einfügen eines <code>Transaction</code>-Objekts in die Datenbank. Dabei
   * wird auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
   * berichtigt.
   * 
   * @param t das zu speichernde Objekt
   * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
   *         <code>id</code>.
   */
  public Transaction insert(Transaction t) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Zunächst schauen wir nach, welches der momentan höchste
       * Primärschlüsselwert ist.
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM transactions ");

      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
      if (rs.next()) {
        /*
         * a erhält den bisher maximalen, nun um 1 inkrementierten
         * Primärschlüssel.
         */
        t.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate("INSERT INTO transactions (id, sourceAccount, targetAccount, amount) "
            + "VALUES ("
            + t.getId()
            + ","
            + t.getSourceAccountID()
            + ","
            + t.getTargetAccountID() + "," + t.getAmount() + ")");
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    /*
     * Rückgabe, der evtl. korrigierten Buchung.
     * 
     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
     * Objekte übergeben werden, wäre die Anpassung des Transaction-Objekts auch
     * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
     * explizite Rückgabe von t ist eher ein Stilmittel, um zu signalisieren,
     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
     */
    return t;
  }

  /**
   * Wiederholtes Schreiben eines Objekts in die Datenbank.
   * 
   * @param t das Objekt, das in die DB geschrieben werden soll
   * @return das als Parameter übergebene Objekt
   */
  public Transaction update(Transaction t) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE transactions SET " + "sourceAccount=\""
          + t.getSourceAccountID() + "\", " + "targetAccount=\""
          + t.getTargetAccountID() + "\", " + "amount=\"" + t.getAmount()
          + "\" " + "WHERE id=" + t.getId());
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Um Analogie zu insert(Transaction t) zu wahren, geben wir t zurück
    return t;
  }

  /**
   * Löschen der Daten eines <code>Transaction</code>-Objekts aus der Datenbank.
   * 
   * @param t das aus der DB zu löschende "Objekt"
   */
  public void delete(Transaction t) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM transactions " + "WHERE id=" + t.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Löschen sÄmtlicher Buchungen (<code>Transaction</code>-Objekte) eines
   * Kontos. Diese Methode sollte aufgerufen werden, bevor ein
   * <code>Account</code> -Objekt gelöscht wird.
   * 
   * @param a das <code>Account</code>-Objekt, zu dem die Buchungen gehören
   */
  public void deleteTransactionsOf(Account a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM transactions " + "WHERE sourceAccount="
          + a.getId());
      stmt.executeUpdate("DELETE FROM transactions " + "WHERE targetAccount="
          + a.getId());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Auslesen des zugehörigen <code>Account</code>-Objekts zu einer gegebenen
   * Buchung.
   * 
   * @param t die Buchung, dessen Quellkonto wir auslesen möchten
   * @return ein Objekt, das das Quellkonto der Buchung darstellt
   */
  public Account getSourceAccount(Transaction t) {
    /*
     * Wir bedienen uns hier einfach des AccountMapper. Diesem geben wir einfach
     * den in dem Transaction-Objekt enthaltenen Fremdschlüssel für das Konto.
     * Der AccountMapper löst uns dann diese ID in ein Objekt auf.
     */
    return AccountMapper.accountMapper().findByKey(t.getSourceAccountID());
  }

  /**
   * Auslesen des zugehörigen <code>Account</code>-Objekts zu einer gegebenen
   * Buchung.
   * 
   * @param t die Buchung, dessen Zielkonto wir auslesen möchten
   * @return ein Objekt, das das Zielkonto der Buchung darstellt
   */
  public Account getTargetAccount(Transaction t) {
    /*
     * Wir bedienen uns hier einfach des AccountMapper. Diesem geben wir einfach
     * den in dem Transaction-Objekt enthaltenen Fremdschlüssel für das Konto.
     * Der AccountMapper löst uns dann diese ID in ein Objekt auf.
     */
    return AccountMapper.accountMapper().findByKey(t.getTargetAccountID());
  }

}
