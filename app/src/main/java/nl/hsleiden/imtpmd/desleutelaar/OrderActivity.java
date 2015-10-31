package nl.hsleiden.imtpmd.desleutelaar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "nl.hsleiden.imtpmd.desleutelaar.MESSAGE";
    String itemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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
        // haal de waarden uit de velden
        EditText namefield = (EditText)findViewById(R.id.nameField);
        String name = namefield.getText().toString();

        EditText addressfield = (EditText)findViewById(R.id.addressField);
        String address = addressfield.getText().toString();

        EditText telephonefield = (EditText)findViewById(R.id.telephoneField);
        String telephone = telephonefield.getText().toString();

        EditText emailfield = (EditText)findViewById(R.id.emailField);
        String email = emailfield.getText().toString();

        // even testen
        Log.d("Gegevens:", name + " " + address + " " + telephone + " " + email);

        // laat een toast zien
        Context context = getApplicationContext();
        CharSequence text = "Bestelling geplaatst!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // ga terug naar hoofdscherm
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}