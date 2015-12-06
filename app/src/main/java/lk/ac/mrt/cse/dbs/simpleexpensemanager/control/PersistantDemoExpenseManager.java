package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.io.Serializable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by TOSHIBA on 12/6/2015.
 */
public class PersistantDemoExpenseManager extends ExpenseManager implements Serializable {

    Context context;

    public PersistantDemoExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        setup();

    }

    public void setup(){
        TransactionDAO persistanttransactionDAO = new PersistantTransactionDAO(context);
        setTransactionsDAO(persistanttransactionDAO);

        AccountDAO persistantAccountDAO = new PersistantAccountDAO(context);
        setAccountsDAO(persistantAccountDAO);

        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);
    }
}
