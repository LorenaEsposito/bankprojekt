package de.hdm.thies.bankProjekt.shared.bo;

/**
 * Realisierung einer exemplarischen Bankbeschreibung. Eine Bank besitzt einen
 * Namen, eine Adresse usw.
 * 
 * @author thies
 * @version 1.0
 */
public class Bank extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Name der Bank
   */
  private String name = "";

  /**
   * Straße und Hausnummer der Bank.
   */
  private String street = "";

  /**
   * Postleitzahl der Bank.
   */
  private int zip = 0;

  /**
   * Ortsbezeichnung der Bank.
   */
  private String city = "";

  /**
   * No Argument Constructor
   */
  public Bank() {
    super();
  }

  /**
   * Auslesen des Orts.
   */
  public String getCity() {
    return city;
  }

  /**
   * Auslesen des Namens der Bank, z.B. "Sparkasse Stuttgart"
   */
  public String getName() {
    return name;
  }

  /**
   * Auslesen der Straßenadresse der Bank
   */
  public String getStreet() {
    return street;
  }

  /**
   * Auslesen der Postleitzahl.
   */
  public int getZip() {
    return zip;
  }

  /**
   * Setzen des Orts der Bank, z.B. "Stuttgart"
   */
  public void setCity(String string) {
    city = string;
  }

  /** 
   * Setzen des Namens der Bank.
   */
  public void setName(String string) {
    name = string;
  }

  /**
   * Setzen der Straßenadresse der Bank, z.B. "Nobelstr. 10".
   */
  public void setStreet(String string) {
    street = string;
  }

  /**
   * Setzen der Postleitzahl
   */
  public void setZip(int i) {
    zip = i;
  }

}
