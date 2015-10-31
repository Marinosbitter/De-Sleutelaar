package nl.hsleiden.imtpmd.desleutelaar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class OrderActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

    EditText namefield;
    EditText addressfield;
    EditText telephonefield;
    EditText emailfield;

    public final static String EXTRA_MESSAGE = "nl.hsleiden.imtpmd.desleutelaar.MESSAGE";
    String itemValue;

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
        Intent intent = getIntent();
        itemValue = intent.getStringExtra(LockInfoActivity.EXTRA_MESSAGE);

        TextView lockName;
        lockName = (TextView) findViewById(R.id.lockInfoField);
        lockName.setText(itemValue);
    }

    public void openLockInfoActivity(View view) {
        Intent intent = new Intent(this, LockInfoActivity.class);
        String message = itemValue;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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
        // even testen
        Log.d("Gegevens:", name + " " + address + " " + telephone + " " + email);

        Context context = getApplicationContext();
        CharSequence text = "Bestelling geplaatst!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}