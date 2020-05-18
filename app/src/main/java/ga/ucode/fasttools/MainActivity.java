package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CardView weatherDashboardCard;
    private ImageView weatherDashboardImage;
    private TextView weatherDashboardText;

    private CardView notesDashboardCard;

    private CardView hackMenuCard;

    private CardView IUSMSCard;

    public void cardClicked(View v){

        Intent i;

        switch (v.getId()){
            case R.id.weatherDashboardCard:
                i = new Intent(MainActivity.this,Weather.class);
                Pair[] pairs1 = new Pair[3];
                pairs1[0] = new Pair<View, String>(weatherDashboardImage,"weatherDashIconTransition");
                pairs1[1] = new Pair<View, String>(weatherDashboardText,"textDashTransition");
                pairs1[2] = new Pair<View, String>(weatherDashboardCard,"weatherCardTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs1);

                startActivity(i, options.toBundle());
                break;
            case R.id.notesDashboardCard:
                i = new Intent(MainActivity.this,NotesActivity.class);
                Pair[] pairs2 = new Pair[1];
                pairs2[0] = new Pair<View, String>(notesDashboardCard,"notesCardTransition");
                ActivityOptions options2 = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs2);
                startActivity(i, options2.toBundle());
                break;
            case R.id.hackMenuCard:
                i = new Intent(MainActivity.this,HackActivity.class);
                Pair[] pairs3 = new Pair[1];
                pairs3[0] = new Pair<View, String>(hackMenuCard,"startHackActivity");
                ActivityOptions options3 = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs3);
                startActivity(i, options3.toBundle());
                break;
            case R.id.IUSMS:
                i = new Intent(MainActivity.this,IUSMSLoginActivity.class);
                Pair[] pairs4 = new Pair[1];
                pairs4[0] = new Pair<View, String>(IUSMSCard,"iusmsLoginActivity");
                ActivityOptions options4 = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs4);
                startActivity(i, options4.toBundle());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDashboardCard = findViewById(R.id.weatherDashboardCard);
        weatherDashboardImage = findViewById(R.id.weatherDashboardIcon);
        weatherDashboardText = findViewById(R.id.weatherDashboardText);

        notesDashboardCard = findViewById(R.id.notesDashboardCard);

        hackMenuCard = findViewById(R.id.hackMenuCard);

        IUSMSCard = findViewById(R.id.IUSMS);
    }
}
