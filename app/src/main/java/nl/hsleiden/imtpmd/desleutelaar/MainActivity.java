package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Roep een instantie van je class DatabaseHelper aan.
// Merk op dat de DatabaseHelper synchronised is en er is altijd maar 1 instantie.
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CourseColumn.NAME, "IMTPMD");
        values.put(DatabaseInfo.CourseColumn.ECTS, "3");
        values.put(DatabaseInfo.CourseColumn.GRADE, "5.5");

// INSERT dit values object in DE (ZELFGEMAAKTE) RIJ COURSE,
        dbHelper.insert(DatabaseInfo.CourseTables.COURSE, null, values);




        //    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        Cursor rs = dbHelper.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen

// Haalt uit de resultset
        String name = rs.getString(rs.getColumnIndex("name"));

// Even checken of dit goed binnen komt
        Log.d("Michiel deze gevonden=", "deze :" + name);

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
