package nl.hsleiden.imtpmd.desleutelaar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LockInfoActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "nl.hsleiden.imtpmd.desleutelaar.MESSAGE";
    String itemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_info);

        // Get the message from the intent
        Intent intent = getIntent();
        itemValue = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Find the info field and sets it to the lock value
        TextView lockName;
        lockName = (TextView) findViewById(R.id.extensiveInfoField);
        lockName.setText(itemValue);

    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openOrderActivity(View view) {
        Log.d("De bestel knop", "is succesvol ingedrukt!");
        Intent intent = new Intent(this, OrderActivity.class);
        String message = itemValue;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}