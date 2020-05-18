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

public class IUSMSLoginActivity extends AppCompatActivity {

    EditText Enroll;
    EditText Password;
    Button Submit;
    CardView iusmsLoginCard;

    public String EnrollmentText, PasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iusmslogin);

        internetStatus();

        Enroll = findViewById(R.id.enrollmentNumber);
        Password = findViewById(R.id.password);
        Submit = findViewById(R.id.submit);
        iusmsLoginCard = findViewById(R.id.iusmslogincard);

    }

    public void submit (View view){
        internetStatus();
        if (internetStatus()) {
            EnrollmentText = Enroll.getText().toString();
            PasswordText = Password.getText().toString();

            Intent i = new Intent(IUSMSLoginActivity.this, IUSMSActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(iusmsLoginCard, "iusmsCardTransition");
            i.putExtra("enroll", EnrollmentText);
            i.putExtra("pass", PasswordText);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(IUSMSLoginActivity.this, pairs);

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
