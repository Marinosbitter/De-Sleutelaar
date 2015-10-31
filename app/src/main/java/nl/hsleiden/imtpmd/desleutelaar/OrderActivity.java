package nl.hsleiden.imtpmd.desleutelaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
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
    }
}