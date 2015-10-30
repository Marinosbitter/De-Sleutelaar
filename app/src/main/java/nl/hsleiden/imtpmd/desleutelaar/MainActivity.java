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

import com.google.gson.Gson;

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    // ListView Clicked item index
    int itemPosition;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    // ListView Clicked item value
    String itemValue;

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
        Cursor lockRS = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{"*"}, null, null, null, null, null);
        lockRS.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen
        // Haalt uit de resultset
        String lockName = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME));
        String info = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO));
        String infoEx = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT));
        String prize = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.PRIZE));

        // Even checken of dit goed binnen komt
        Log.d("Marijn_Lock 1", "Name :" + lockName);
        Log.d("Marijn_Lock 1", "info :" + info);
        Log.d("Marijn_Lock 1", "infoEx :" + infoEx);
        Log.d("Marijn_Lock 1", "prize :" + prize);

        lockRS.moveToNext();
        // Haalt uit de resultset
        lockName = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME));
        info = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO));
        infoEx = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.INFO_EXCERPT));
        prize = lockRS.getString(lockRS.getColumnIndex(DatabaseInfo.LockColumn.PRIZE));

        // Even checken of dit goed binnen komt
        Log.d("Marijn_Lock 2", "Name :" + lockName);
        Log.d("Marijn_Lock 2", "info :" + info);
        Log.d("Marijn_Lock 2", "infoEx :" + infoEx);
        Log.d("Marijn_Lock 2", "prize :" + prize);




        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[]{
                "Yale Oplegslot",
                "Nemef bezetslot",
                "Nacht oplegslot",
                "Fiam insteekpenslot",
                "Cilinderslot",
                "Economy badkamerslot",
                "Zaso penslot",
                "ART5 pro-tect slot"
        };

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
                newtext.setText("Dit is een " + itemValue + ". Dit slot staat op de " + itemPosition + "e plaats in de lijst. Met dit slot kan je deuren op slot doen.");

                Button btn = (Button) findViewById(R.id.moreInfoButton);
                btn.setEnabled(true);
                btn.setClickable(true);
            }

        });
    }

    public void openMeerInfo(View view) {
        Log.d("De meer knop", "is succesvol ingedrukt!");
        Intent intent = new Intent(this, LockInfoActivity.class);
        intent.putExtra(EXTRA_MESSAGE, itemValue);
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
