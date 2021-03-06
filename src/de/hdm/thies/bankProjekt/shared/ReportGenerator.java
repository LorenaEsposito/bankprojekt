package de.hdm.thies.bankProjekt.shared;

import de.hdm.thies.bankProjekt.shared.bo.Bank;
import de.hdm.thies.bankProjekt.shared.bo.Customer;
import de.hdm.thies.bankProjekt.shared.report.AllAccountsOfAllCustomersReport;
import de.hdm.thies.bankProjekt.shared.report.AllAccountsOfCustomerReport;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. Diese Schnittstelle benutzt die gleiche Realisierungsgrundlage wir
 * das Paar {@link BankAdministration} und {@lBankAdministrationImplImpl}. Zu
 * technischen Erläuterung etwa bzgl. GWT RPC bzw. {@link RemoteServiceServlet}
 * siehe {@link BankAdministration} undBankAdministrationImpltungImpl}.
 * </p>
 * <p>
 * Ein ReportGenerator bietet die Möglichkeit, eine Menge von Berichten
 * (Reports) zu erstellen, die Menge von Daten bzgl. bestimmter Sachverhalte des
 * Systems zweckspezifisch darstellen.
 * </p>
 * <p>
 * Die Klasse bietet eine Reihe von <code>create...</code>-Methoden, mit deren
 * Hilfe die Reports erstellt werden können. Jede dieser Methoden besitzt eine
 * dem Anwendungsfall entsprechende Parameterliste. Diese Parameter benötigt der
 * der Generator, um den Report erstellen zu können.
 * </p>
 * <p> 
 * Bei neu hinzukommenden Bedarfen an Berichten, kann diese Klasse auf einfache
 * Weise erweitert werden. Hierzu können zusätzliche <code>create...</code>
 * -Methoden implementiert werden. Die bestehenden Methoden bleiben davon
 * unbeeinflusst, so dass bestehende Programmlogik nicht verändert werden muss.
 * </p>
 * 
 * @author thies
 */
@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {

  /**
   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
   *BankAdministrationImpltungImpl} notwendig. Bitte diese Methode direkt nach der
   * Instantiierung aufrufen.
   * 
   * @throws IllegalArgumentException
   */
  public void init() throws IllegalArgumentException;

  /**
   * Setzen der zugeordneten Bank.
   * 
   * @para Bank-Objekt
   * @throws IllegalArgumentException
   */
  public void setBank(Bank b) throws IllegalArgumentException;

  /**
   * Erstellen eines <code>AllAccountsOfCustomerReport</code>-Reports. Dieser
   * Report-Typ stellt sämtliche Konten eines Kunden dar.
   * 
   * @param c eine Referenz auf das Kundenobjekt bzgl. dessen der Report
   *          erstellt werden soll.
   * @return das fertige Reportobjekt
   * @throws IllegalArgumentException
   * @see AllAccountsOfCustomerReport
   */
  public abstract AllAccountsOfCustomerReport createAllAccountsOfCustomerReport(
      Customer c) throws IllegalArgumentException;

  /**
   * Erstellen eines <code>AllAccountsOfAllCustomersReport</code>-Reports.
   * Dieser Report-Typ stellt sämtliche Konten aller Kunden dar.
   * 
   * @return das fertige Reportobjekt
   * @throws IllegalArgumentException
   * @see AllAccountsOfAllCustomersReport
   */
  public abstract AllAccountsOfAllCustomersReport createAllAccountsOfAllCustomersReport()
      throws IllegalArgumentException;
}