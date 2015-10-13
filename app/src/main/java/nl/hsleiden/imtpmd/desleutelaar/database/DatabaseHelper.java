package nl.hsleiden.imtpmd.desleutelaar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.google.gson.Gson;


/**
 * Created by Marijn on 13/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;

    public static final String dbName = "sleutelaar.db";
    public static final int dbVersion = 1;

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
//        // Ik maak een string waar de content van de courses in komt te staan.
//        String json = "[{name: ' module 1', ects: 3, grade:6}," +
//                "{name: ' module 2', ects: 3, grade:6}, " +
//                "{name: ' module 3', ects: 1, grade:6}]";
//
//        Gson gson = new Gson();
//        CourseModel[] courses = gson.fromJson(json, CourseModel[].class);
//        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
//
//        for(CourseModel course : courses) {
//            ContentValues values = new ContentValues();
//            values.put(DatabaseInfo.CourseColumn.NAME, course.name);
//            values.put(DatabaseInfo.CourseColumn.ECTS, course.ects);
//            values.put(DatabaseInfo.CourseColumn.GRADE, course.grade);
//            dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);
//        }
//
//
//        db.execSQL("CREATE TABLE " + DatabaseInfo.CourseTables.COURSE + " (" +
//                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        DatabaseInfo.CourseColumn.NAME + " TEXT," +
//                        DatabaseInfo.CourseColumn.ECTS + " TEXT," +
//                        DatabaseInfo.CourseColumn.GRADE + " REAL);"
//        );
//
//            db.execSQL("");

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