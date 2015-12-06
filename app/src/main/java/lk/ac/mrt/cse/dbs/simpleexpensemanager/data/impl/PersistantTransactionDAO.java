package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBlayer.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBlayer.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by TOSHIBA on 12/6/2015.
 */
public class PersistantTransactionDAO implements TransactionDAO {
    SQLiteDatabase DB = null;
    DatabaseHandler dbHandler = null;

    public PersistantTransactionDAO (Context context){
        if (dbHandler == null){
            dbHandler = new DatabaseHandler(context);
        }
        DB = dbHandler.getWritableDatabase();
    }

    SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);


    @Override
    public void logTransaction(Date date, String accountNo,String transactionID, ExpenseType expenseType, double amount) {
        ContentValues values = new ContentValues();
        values.put(dbHandler.accountNo, accountNo);
        values.put(dbHandler.transaction_ID, transactionID);
        values.put(dbHandler.date, String.valueOf(date));
        values.put(dbHandler.expenseType, String.valueOf(expenseType));
        values.put(dbHandler.amount, amount);

        DB.insert(dbHandler.TRANSACTION_TABLE, null, values);
        DB.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT * FROM" + dbHandler.TRANSACTION_TABLE;
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Transaction transaction = new Transaction();
                transaction.setAccountNo(cursor.getString(0));
                transaction.setTransactionID(cursor.getString(1));
                try {
                    transaction.setDate(format.parse(cursor.getString(2)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setExpenseType(ExpenseType.valueOf(cursor.getString(3)));
                //Adding account to list
                transactionList.add(transaction);
            }while (cursor.moveToNext());
        }
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String pagingQuery = "SELECT * FROM " + dbHandler.TRANSACTION_TABLE + "LIMIT " + limit;
        Cursor cursor = DB.rawQuery(pagingQuery, null);

        if (cursor.moveToFirst()){
            do {
                Transaction transaction = new Transaction();
                transaction.setAccountNo(cursor.getString(0));
                transaction.setTransactionID(cursor.getString(1));
                try {
                    transaction.setDate(format.parse(cursor.getString(2)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setExpenseType(ExpenseType.valueOf(cursor.getString(3)));
                //Adding account to list
                transactionList.add(transaction);
            }while (cursor.moveToNext());
        }
        return transactionList;

    }

}
