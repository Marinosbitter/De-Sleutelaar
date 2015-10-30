package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    List<List<String>> listOfLocks = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // PUT INFO IN DATABASE
        // Ik maak een string waar de content van de courses in komt te staan.
        String json = "[{lockName: 'lock 1', infoExerpt: 'bla', info: 'blabla', prize: 4}," +
                "{lockName: 'lock 2', infoExerpt: 'bla', info: 'blabla', prize: 2}," +
                "{lockName: 'lock 3', infoExerpt: 'bla', info: 'blabla', prize: 3}]";

        Gson gson = new Gson();
        LockModel[] lockModels = gson.fromJson(json, LockModel[].class);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        for (LockModel lockModel : lockModels) {
            ContentValues values = new ContentValues();
            values.put(DatabaseInfo.LockColumn.LOCKNAME, lockModel.lockName);
            values.put(DatabaseInfo.LockColumn.INFO, lockModel.info);
            values.put(DatabaseInfo.LockColumn.INFO_EXCERPT, lockModel.infoExerpt);
            values.put(DatabaseInfo.LockColumn.PRIZE, lockModel.prize);
            dbHelper.insert(DatabaseInfo.LockTables.LOCK, null, values);
        }

        // GET INFO FROM DATABASE
        //    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        Cursor lockRS = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{"*"}, null, null, null, null, null);
        lockRS.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen

        int locksInDb = lockRS.getColumnCount();

        for (int i = 0; i <= locksInDb; i++) {
            List<String> lockList = new ArrayList();
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.PRIZE)));

            listOfLocks.add(lockList);
            lockRS.moveToNext();
        }

       // Even checken of dit goed binnen komt
        Log.d("Marijn_Lock 1", "Name :" + listOfLocks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
