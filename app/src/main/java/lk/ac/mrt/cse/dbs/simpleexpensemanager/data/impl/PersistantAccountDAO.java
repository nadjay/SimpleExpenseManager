package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBlayer.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBlayer.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by TOSHIBA on 12/6/2015.
 */
public class PersistantAccountDAO implements AccountDAO {

    SQLiteDatabase db = null;
    DatabaseHandler DBHandler = null;

    public PersistantAccountDAO (Context context){
        if (DBHandler == null){
            DBHandler = new DatabaseHandler(context);
        }
        db = DBHandler.getWritableDatabase();
    }


    @Override
    public List<String> getAccountNumbersList() {
        Cursor accountNumbersList = db.query(DBHandler.ACCOUNT_TABLE, new String[]{DBHandler.accountNo},null,null,null,null,null);

        List<String> accNoList = new ArrayList<>();
            while(accountNumbersList.moveToNext()){
                accNoList.add(accountNumbersList.getString(accountNumbersList.getColumnIndex(DBHandler.accountNo)));
            }
        return  accNoList;

    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList = new ArrayList<Account>();
        String selectQuery = "SELECT * FROM " + DBHandler.ACCOUNT_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Account account = new Account();
                account.setAccountNo(cursor.getString(0));
                account.setBankName(cursor.getString(1));
                account.setAccountHolderName(cursor.getString(2));
                account.setBalance(Double.parseDouble(cursor.getString(3)));
                //Adding account to list
                accountList.add(account);
            }while (cursor.moveToNext());
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database = DBHandler.getReadableDatabase();

        Cursor cursor = database.query(DBHandler.ACCOUNT_TABLE, new String[]{DBHandler.accountNo, DBHandler.bankName, DBHandler.accountHolderName, DBHandler.balance}, DBHandler.accountNo+"=?", new String[]{accountNo},null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)));
        return account;


    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(DBHandler.accountNo, account.getAccountNo());
        values.put(DBHandler.bankName, account.getBankName());
        values.put(DBHandler.accountHolderName, account.getAccountHolderName());
        values.put(DBHandler.balance, account.getBalance());

        db.insert(DBHandler.ACCOUNT_TABLE, null, values);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        db.delete(DBHandler.ACCOUNT_TABLE, DBHandler.accountNo + "=?", new String[]{accountNo});
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        ContentValues values = new ContentValues();
        Account account = getAccount(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                values.put(DBHandler.balance,account.getBalance()-amount );
                break;
            case INCOME:
                values.put(DBHandler.balance, account.getBalance()+amount);
                break;
        }
    }
}
