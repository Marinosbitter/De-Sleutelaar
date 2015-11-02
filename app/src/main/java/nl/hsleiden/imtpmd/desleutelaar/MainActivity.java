package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.android.internal.util.Predicate;
import com.google.gson.Gson;

import org.w3c.dom.Text;

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

        Intent intent = getIntent();
        itemValue = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        if (!isOnline()) {
            // laat een toast zien
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.offlineText);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        // PUT INFO IN DATABASE
        // Ik maak een string waar de content van de courses in komt te staan.
        String json =
                "[{lockName: 'Yale Oplegslot', infoExerpt: 'Dit is een Yale Oplegslot', info: 'Vaste buitencilinder. 3 sleutels. Extra sleutel kopieën direct mee te bestellen. Sluitkast. Schroeven. Rozet. Geen keurmerk.', prize: 65}," +
                "{lockName: 'Nemef bezetslot', infoExerpt: 'Dit is een Nemef bezetslot', info: 'Op zoek naar de ultieme beveiliging voor uw huis? Kies dan voor het Nemef ENTR elektronisch slot. Nemef ENTR is de revolutionaire, slimme vergrendelingsoplossing waarmee je de deur via je smartphone, tablet of andere apparaten met Bluetooth kunt bedienen. Veilig en comfortabel.', prize: 284}," +
                "{lockName: 'Nacht oplegslot', infoExerpt: 'Dit is een nachtoplegslot', info: 'Oplegsloten zijn zeer geschikt om extra veiligheid aan te brengen voor uw pand. Dit slot is eenvoudig te plaatsen, bovendien zijn onze oplegsloten onderhoudsvrij. Zo kunt u met een oplegslot uw woning jarenlang beveiligen.', prize: 84}," +
                "{lockName: 'Fiam insteekpenslot', infoExerpt: 'Dit een Fiam insteekpenslot', info: '2 sterren pen-insteekslot extra zware uitvoering, heeft het SKG**® keurmerk, met zware sluitkom. Dit is een extra zware uitvoering van het bekende pennenslot, speciaal voor diegene die van kwaliteit houden en met minder geen genoegen nemen.', prize: 35}," +
                "{lockName: 'Cilinderslot', infoExerpt: 'Dit is een Cilinderslot', info: 'Standaard kwaliteit. Met name geschikt voor binnendeuren. Gelijksluitend uit voorraad leverbaar. Met 5 permutatiestiften', prize: 55}," +
                "{lockName: 'Economy badkamerslot', infoExerpt: 'Dit is een Economy badkamerslot', info: 'Geschikt voor binnendeuren in woningbouw instekend. Kast afmeting 117 x 80 x 17 mm. Krukgat 8 mm kruk-sleutelgat hart op hart 63 mm. Met afgeronde voorplaat 174 x 20 mm. Compleet met sluitplaat. Uitvoering voorplaat roestvaststaal dagschoot messing nachtschoot messing. Rechts en links bruikbaar. ', prize: 21}," +
                "{lockName: 'Zaso penslot', infoExerpt: 'Dit is een zaso penslot', info: 'Standaard geleverd met 3 kruissleutels, sluitkom en sleutelplaatjes, in verschillend sluitende uitvoering. Ook gelijksluitend (tot 20 stuks) verkrijgbaar dan geleverd met 2 ipv 3 sleutels per slot (zelfde sleutel -2 per slot- voor alle GS pensloten)', prize: 500}," +
                "{lockName: 'ART5 pro-tect slot', infoExerpt: 'Dit is een ART5 pro-tect slot', info: 'ART 5 TNO/ART gekeurd. Beugeldikte hangslot: 15,5mm. Schakeldikte ketting: 14,5mm. Gehard staal Dubbelsluitend. Dit kettingslot heeft de hoogste type goedkeuring en is geschikt voor dure motoren. Verzekeringen eisen vaak een ART5 slot als het slot een hoge waarde heeft. Met dit slot kan een motor op een plaats blijven staan waar een hoog diefstal risico is.', prize: 54}]";

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

        if (itemValue != null && !itemValue.isEmpty()){
            Log.d("asdf", "er is een knop gekozen" + itemValue);
            TextView newtext = (TextView) findViewById(R.id.lockInfoField);
            for (int i = 0; i < listOfLocks.size(); i++) {
                    newtext.setText(listOfLocks.get(i).get(2));
            }

            Button btn = (Button) findViewById(R.id.moreInfoButton);
            btn.setEnabled(true);
            btn.setClickable(true);
        }
    }

    public void openLockInfoActivity(View view) {
        Log.d("De meer knop", "is succesvol ingedrukt!");
        Intent intent = new Intent(this, LockInfoActivity.class);
        String message = itemValue;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
