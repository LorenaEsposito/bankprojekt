package de.hdm.thies.bankProjekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.bo.Bank;
import de.hdm.thies.bankProjekt.shared.bo.Customer;
import de.hdm.thies.bankProjekt.shared.report.AllAccountsOfAllCustomersReport;
import de.hdm.thies.bankProjekt.shared.report.AllAccountsOfCustomerReport;

/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies
 */
public interface ReportGeneratorAsync {

  void createAllAccountsOfAllCustomersReport(
      AsyncCallback<AllAccountsOfAllCustomersReport> callback);

  void createAllAccountsOfCustomerReport(Customer c,
      AsyncCallback<AllAccountsOfCustomerReport> callback);

  void init(AsyncCallback<Void> callback);

  void setBank(Bank b, AsyncCallback<Void> callback);

}
