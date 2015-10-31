package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.internal.util.Predicate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    List<List<String>> listOfLocks = new ArrayList();
    ListView listView;
    // ListView Clicked item index
    int itemPosition;
    public final static String EXTRA_MESSAGE = "nl.hsleiden.imtpmd.desleutelaar.MESSAGE";
    // ListView Clicked item value
    String itemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // PUT INFO IN DATABASE
        // Ik maak een string waar de content van de courses in komt te staan.
        String json = "[{lockName: 'lock 1', infoExerpt: 'Dit is slotje 1', info: 'blabla', prize: 4}," +
                "{lockName: 'lock 2', infoExerpt: 'Dit is slotje 2', info: 'blabla', prize: 2}," +
                "{lockName: 'lock 3', infoExerpt: 'Dit is slotje 3', info: 'blabla', prize: 3}," +
                "{lockName: 'lock 4', infoExerpt: 'Dit is slotje 4', info: 'Slotje 4 is een geile donder', prize: 17}]";

        Gson gson = new Gson();
        LockModel[] lockModels = gson.fromJson(json, LockModel[].class);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        for (LockModel lockModel : lockModels) {
            Cursor checkLock = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{DatabaseInfo.LockColumn.LOCKNAME}, DatabaseInfo.LockColumn.LOCKNAME + " = '" + lockModel.lockName + "'", null, null, null, null);
            if (checkLock.getCount() == 0) {
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.LockColumn.LOCKNAME, lockModel.lockName);
                values.put(DatabaseInfo.LockColumn.INFO, lockModel.info);
                values.put(DatabaseInfo.LockColumn.INFO_EXCERPT, lockModel.infoExerpt);
                values.put(DatabaseInfo.LockColumn.PRIZE, lockModel.prize);
                dbHelper.insert(DatabaseInfo.LockTables.LOCK, null, values);
            }
        }

        // GET INFO FROM DATABASE
        //    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        Cursor lockRS = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{"*"}, null, null, null, null, null);
        lockRS.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen

        while (!lockRS.isAfterLast()) {
            List<String> lockList = new ArrayList();

            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT)));
            lockList.add(lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.PRIZE)));

            listOfLocks.add(lockList);
            lockRS.moveToNext();
        }

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[listOfLocks.size()];

        for (int i = 0; i < listOfLocks.size(); i++) {
            values[i] = listOfLocks.get(i).get(0);
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition = position + 1;

                // ListView Clicked item value
                itemValue = (String) listView.getItemAtPosition(position);

                TextView newtext = (TextView) findViewById(R.id.lockInfoField);
                for (int i = 0; i < listOfLocks.size(); i++) {
                    if (itemValue == listOfLocks.get(i).get(0)) {
                        newtext.setText(listOfLocks.get(i).get(2));

                    }
                }

                Button btn = (Button) findViewById(R.id.moreInfoButton);
                btn.setEnabled(true);
                btn.setClickable(true);
            }
        });
    }

    public void openLockInfoActivity(View view) {
        Log.d("De meer knop", "is succesvol ingedrukt!");
        Intent intent = new Intent(this, LockInfoActivity.class);
        String message = itemValue;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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
