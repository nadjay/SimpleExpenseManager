package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBlayer;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TOSHIBA on 12/6/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "130244E";

    public static final String ACCOUNT_TABLE = "Accounts";

    public static final String TRANSACTION_TABLE = "Transactions";

    public  static final String accountNo = "accountNo";
    public  static  final String bankName = "bankName";
    public  static  final String accountHolderName = "accountHolderName";
    public  static final String balance = "balance";

    public  static final String date = "date";
    public static final String expenseType = "expenseType";
    public static String amount = "amount";
    public  static  String transaction_ID = "transaction_ID";

    public  static final String ACCOUNT_TABLE_CREATE ="CREATE TABLE IF NOT EXISTS" + ACCOUNT_TABLE + "(" + accountNo + "varchar(20) PRIMARY KEY," + bankName + "varchar(30)," + accountHolderName + "varchar(30)," + balance + "double" + ")";
    public static final String TRANSACTION_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS" + TRANSACTION_TABLE + "(" + accountNo + "varchar(20)," + transaction_ID + "varchar(20) PRIMARY KEY," + date + "date," + expenseType + "varchar(10)," + amount + "double" + ")";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ACCOUNT_TABLE_CREATE);
        db.execSQL(TRANSACTION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
