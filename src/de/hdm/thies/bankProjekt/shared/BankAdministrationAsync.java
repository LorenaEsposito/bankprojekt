package de.hdm.thies.bankProjekt.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.thies.bankProjekt.shared.bo.*;

/**
 * Das asynchrone Gegenstück des Interface {@link BankAdministration}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link BankAdministration}.
 * 
 * @author thies
 */
public interface BankAdministrationAsync {
  void save(Customer c, AsyncCallback<Void> callback);

  void createAccountFor(Customer c, AsyncCallback<Account> callback);

  void createCustomer(String first, String last,
      AsyncCallback<Customer> callback);

  void getAccountsOf(Customer c, AsyncCallback<Vector<Account>> callback);

  void getCustomerByName(String lastName,
      AsyncCallback<Vector<Customer>> callback);

  void getAllAccounts(AsyncCallback<Vector<Account>> callback);

  void getAccountById(int id, AsyncCallback<Account> callback);
  
  void getAllCustomers(AsyncCallback<Vector<Customer>> callback);

  void getBank(AsyncCallback<Bank> callback);

  void getCustomerById(int id, AsyncCallback<Customer> callback);

  void save(Account a, AsyncCallback<Void> callback);

  void init(AsyncCallback<Void> callback);

  void createTransactionFor(Account source, Account target, float value,
      AsyncCallback<Transaction> callback);

  void getBalanceOf(Account k, AsyncCallback<Float> callback);

  void setBank(Bank b, AsyncCallback<Void> callback);

  void delete(Customer c, AsyncCallback<Void> callback);

  void delete(Transaction t, AsyncCallback<Void> callback);

  void delete(Account a, AsyncCallback<Void> callback);

  void getDebitsOf(Account k, AsyncCallback<ArrayList<Transaction>> callback);

  void getCreditsOf(Account k, AsyncCallback<ArrayList<Transaction>> callback);

  void createWithdrawal(Account customerAccount, float amount,
      AsyncCallback<Transaction> callback);

  void createDeposit(Account customerAccount, float amount,
      AsyncCallback<Transaction> callback);
}
