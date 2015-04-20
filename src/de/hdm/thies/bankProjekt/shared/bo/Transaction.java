package de.hdm.thies.bankProjekt.shared.bo;

/**
 * <p>
 * Realisierung einer exemplarischen Buchung. Eine Buchung zeichnet sich dadurch
 * aus, dass sie ein Quellkonto und ein Zielkonto besitzt, zwischen denen ein
 * Betrag, der als Geld interopretiert wird umgebucht wird.
 * </p>
 * 
 * @author thies
 * @version 1.0
 * 
 */
public class Transaction extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Der Betrag der Buchung. Dieser Betrag kann als Geldwert interpretiert
   * werden. Da ein Buchungssystem stets mit einer einzigen Währung arbeitet, 
   * ist deren Repräsentation in der Buchung nicht erforderlich.
   */
  private float amount = 0.0f;
  
  /**
   * Fremdschlüsselbeziehung zum Quellkonto.
   */
  private int sourceAccountID = 0;

  /**
   * Fremdschlüsselbeziehung zum Zielkonto.
   */
  private int targetAccountID = 0;

  /**
   * Auslesen des Buchungswerts.
   */
  public float getAmount() {
    return this.amount;
  }

  /**
   * Setzen des Buchungswerts.
   * @param money der zu buchenden Betrag
   */
  public void setAmount(float money) {
    this.amount = money;
  }

  /**
   * Auslesen des Fremdschlüssels des Quellkontos.
   */
  public int getSourceAccountID() {
    return this.sourceAccountID;
  }

  /**
   * Setzen des Fremdschlüssels des Quellkontos.
   * @param sourceID der Fremdschlüssel
   */
  public void setSourceAccountID(int sourceID) {
    this.sourceAccountID = sourceID;
  }

  /**
   * Auslesen des Fremdschlüssels des Zielkontos.
   * @return der Fremdschlüssel
   */
  public int getTargetAccountID() {
    return this.targetAccountID;
  }

  /**
   * Setzen des Fremdschlüssels des Zielkontos.
   * @param targetID der Fremdschlüssel
   */
  public void setTargetAccountID(int targetID) {
    this.targetAccountID = targetID;
  }

  /**
   * Erzeugen einer textuellen Darstellung der jeweiligen Buchung.
   */
  @Override
public String toString() {
    return super.toString() + "von Account-ID: #" + this.sourceAccountID
        + "an Account-ID: #" + this.targetAccountID;
  }

}
