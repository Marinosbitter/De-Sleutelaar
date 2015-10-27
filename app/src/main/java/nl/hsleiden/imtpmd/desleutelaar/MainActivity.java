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

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        Cursor rs = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen
        // Haalt uit de resultset
        String lockName = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME));
        String info = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.INFO));
        String infoEx = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT));
        String prize = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.PRIZE));

        // Even checken of dit goed binnen komt
        Log.d("Marijn_Lock 1", "Name :" + lockName);
        Log.d("Marijn_Lock 1", "info :" + info);
        Log.d("Marijn_Lock 1", "infoEx :" + infoEx);
        Log.d("Marijn_Lock 1", "prize :" + prize);

        rs.moveToNext();
        // Haalt uit de resultset
        lockName = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME));
        info = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.INFO));
        infoEx = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT));
        prize = rs.getString(rs.getColumnIndex(DatabaseInfo.LockColumn.PRIZE));

        // Even checken of dit goed binnen komt
        Log.d("Marijn_Lock 2", "Name :" + lockName);
        Log.d("Marijn_Lock 2", "info :" + info);
        Log.d("Marijn_Lock 2", "infoEx :" + infoEx);
        Log.d("Marijn_Lock 2", "prize :" + prize);
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
