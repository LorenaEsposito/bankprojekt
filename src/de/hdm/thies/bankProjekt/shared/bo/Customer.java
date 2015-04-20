package de.hdm.thies.bankProjekt.shared.bo;

/**
 * Realisierung einer exemplarischen Kundenklasse. Aus Gründen der Vereinfachung
 * besitzt der Kunden in diesem Demonstrator lediglich einen Vornamen und einen
 * Nachnamen.
 * 
 * @author thies
 * @version 1.0
 */
public class Customer extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Der Vorname des Kunden.
   */
  private String firstName = "";
  
  /**
   * Der Nachname des Kunden.
   */
  private String lastName = "";
  
  /**
   * Auslesen des Vornamens.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Setzen des Vornamens.
   */
  public void setFirstName(String name) {
    this.firstName = name;
  }

  /**
   * Auslesen des Nachnamens.
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Setzen des Nachnamens.
   */
  public void setLastName(String name) {
    this.lastName = name;
  }

  /**
   * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
   * Diese besteht aus dem Text, der durch die <code>toString()</code>-Methode
   * der Superklasse erzeugt wird, ergänzt durch den Vor- und Nachnamen des 
   * jeweiligen Kunden.
   */
  @Override
public String toString() {
    return super.toString() + " " + this.firstName + " " + this.lastName;
  }

}
