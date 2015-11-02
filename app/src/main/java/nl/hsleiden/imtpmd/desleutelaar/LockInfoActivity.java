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

import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseHelper;
import nl.hsleiden.imtpmd.desleutelaar.database.DatabaseInfo;

public class LockInfoActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "nl.hsleiden.imtpmd.desleutelaar.MESSAGE";
    String itemValue;
    String lockTitle = "Lock could not be found";
    String lockInfo = "Ask the awsmome developers of this app what happened (they probably won't know either)";
    String lockPrize = "99";
    TextView lockTitleField;
    TextView lockInfoField;
    TextView lockPrizeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_info);

        // Get the message from the intent
        Intent intent = getIntent();
        itemValue = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        lockTitleField = (TextView) findViewById(R.id.lockTitle);
        lockInfoField = (TextView) findViewById(R.id.extensiveInfoField);
        lockPrizeField = (TextView) findViewById(R.id.lockPrizeField);

        // Get lock info from DB
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        Cursor getLockInfo = dbHelper.query(DatabaseInfo.LockTables.LOCK, new String[]{"*"}, DatabaseInfo.LockColumn.LOCKNAME + " = '" + itemValue + "'", null, null, null, null);
        getLockInfo.moveToFirst();
        if (getLockInfo.getCount() != 0) {
            // Find the info field and sets it to the lock value
            lockTitle = getLockInfo.getString(getLockInfo.getColumnIndex(DatabaseInfo.LockColumn.LOCKNAME));
            lockInfo = getLockInfo.getString(getLockInfo.getColumnIndex(DatabaseInfo.LockColumn.INFO));
            lockPrize = getLockInfo.getString(getLockInfo.getColumnIndex(DatabaseInfo.LockColumn.PRIZE));
        }

        // Find the info field and sets it to the lock value
        lockTitleField = (TextView) findViewById(R.id.lockTitle);
        lockTitleField.setText(lockTitle);
        lockInfoField = (TextView) findViewById(R.id.extensiveInfoField);
        lockInfoField.setText(lockInfo);
        lockPrizeField = (TextView) findViewById(R.id.lockPrizeField);
        lockPrizeField.setText(lockPrize);
    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String message = itemValue;
        intent.putExtra(EXTRA_MESSAGE, message);
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