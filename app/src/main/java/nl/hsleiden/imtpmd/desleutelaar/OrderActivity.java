package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class OrderActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

    EditText namefield;
    EditText addressfield;
    EditText telephonefield;
    EditText emailfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        namefield = (EditText) findViewById(R.id.nameField);
        addressfield = (EditText) findViewById(R.id.addressField);
        telephonefield = (EditText) findViewById(R.id.telephoneField);
        emailfield = (EditText) findViewById(R.id.emailField);

        Cursor customerRS = dbHelper.query(DatabaseInfo.CustomerTables.CUSTOMER, new String[]{"*"}, null, null, null, null, null);

        if (customerRS.getCount() != 0) {
            customerRS.moveToFirst();   // Skip de lege elementen vooraan de rij. Maar : rij kan leeg zijn dus falen

            namefield.setText(customerRS.getString(customerRS.getColumnIndex(DatabaseInfo.CustomerColumn.CUSTOMERNAME)));
            addressfield.setText(customerRS.getString(customerRS.getColumnIndex(DatabaseInfo.CustomerColumn.ADRESS)));
            telephonefield.setText(customerRS.getString(customerRS.getColumnIndex(DatabaseInfo.CustomerColumn.PHONE)));
            emailfield.setText(customerRS.getString(customerRS.getColumnIndex(DatabaseInfo.CustomerColumn.MAIL)));
        }
    }

    public void openLockInfoActivity(View view) {
        Intent intent = new Intent(this, LockInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
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

    public void confirmOrder(View view) {
        String name = namefield.getText().toString();
        String address = addressfield.getText().toString();
        String telephone = telephonefield.getText().toString();
        String email = emailfield.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CustomerColumn.CUSTOMERNAME, "" + name);
        values.put(DatabaseInfo.CustomerColumn.ADRESS, "" + address);
        values.put(DatabaseInfo.CustomerColumn.PHONE, "" + telephone);
        values.put(DatabaseInfo.CustomerColumn.MAIL, "" + email);
        dbHelper.insert(DatabaseInfo.CustomerTables.CUSTOMER, null, values);

        String orderJSON = "{customerName: '"+name+"', adress: '"+address+"', phone: '"+telephone+"', email: '"+email+"', type: 'sf'}]";
        // Use orderJSON to communicate with the database api :)
    }
}