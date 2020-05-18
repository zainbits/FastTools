package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

public class HackActivity extends AppCompatActivity {

    EditText enroll;
    CardView cardView;
    Button button;
    public String enrollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack);

        internetStatus();

        enroll = findViewById(R.id.enrollmentNumber);
        cardView = findViewById(R.id.hackCard);
        button = findViewById(R.id.hackButton);

    }

    public void hackit (View view){
        internetStatus();
        if (internetStatus()) {
            enrollNumber = enroll.getText().toString();

            Intent i = new Intent(HackActivity.this, ResultActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(cardView, "hackTransition");
            i.putExtra("user", enrollNumber);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HackActivity.this, pairs);

            startActivity(i, options.toBundle());
        }
    }

    public final boolean internetStatus(){
        getBaseContext();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService("connectivity");
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.DISCONNECTED && connectivityManager.getNetworkInfo(1).getState() != NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        invokeAlert();
        return false;
    }

    public void invokeAlert(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Internet Connection!!")
                .setMessage("Internet Connection is required to use this feature")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }

    public void finishButton (View v){
        finish();
    }
}
