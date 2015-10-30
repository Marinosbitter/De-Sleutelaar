package nl.hsleiden.imtpmd.desleutelaar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.google.gson.Gson;

import nl.hsleiden.imtpmd.desleutelaar.LockModel;

/**
 * Created by Marijn on 13/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;

    public static final String dbName = "sleutelaar.db";
    public static final int dbVersion = 5;

    public DatabaseHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);
    }

    public static synchronized DatabaseHelper getHelper(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseInfo.LockTables.LOCK + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseInfo.LockColumn.LOCKNAME + " TEXT," +
                        DatabaseInfo.LockColumn.INFO + " TEXT," +
                        DatabaseInfo.LockColumn.INFO_EXCERPT + " TEXT," +
                        DatabaseInfo.LockColumn.PRIZE + " INT);"
        );
        db.execSQL("CREATE TABLE " + DatabaseInfo.CustomerTables.CUSTOMER + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseInfo.CustomerColumn.CUSTOMERNAME + " TEXT," +
                        DatabaseInfo.CustomerColumn.ADRESS + " TEXT," +
                        DatabaseInfo.CustomerColumn.MAIL + " TEXT," +
                        DatabaseInfo.CustomerColumn.PHONE + " TEXT," +
                        DatabaseInfo.CustomerColumn.TYPE + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseInfo.LockTables.LOCK);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseInfo.CustomerTables.CUSTOMER);
        onCreate(db);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values) {
        mSQLDB.insert(table, nullColumnHack, values);
    }


    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy) {
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }
}