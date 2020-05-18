package ga.ucode.fasttools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class Weather extends AppCompatActivity {

    EditText weatherCityName;
    TextView weatherDescription;
    TextView weatherTemp;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherCityName = findViewById(R.id.weatherCityName);
        weatherDescription = findViewById(R.id.weatherDescrition);
        weatherTemp = findViewById(R.id.weatherTemp);

        internetStatus();
        if (internetStatus()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationUpdater();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            locationUpdater();
        }
    }

    public void weatherButton(View v){
        internetStatus();
            if (internetStatus()) {
                getWeather();
            }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                return result;

            }

            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String weatherFetched) {
            super.onPostExecute(weatherFetched);

            try {
                JSONObject jsonObject = new JSONObject(weatherFetched);

                String weatherInfo = jsonObject.getString("weather");
                JSONObject obj = jsonObject.getJSONObject("main");

                Log.i("Weather Content",weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                String message ="";
                String temperature = obj.getDouble("temp") + "°C";
                String humidity ="Humidity: " + obj.getDouble("humidity")+"%";


                for (int i=0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    String icon = jsonPart.getString("icon");

                    if(!main.equals("") && !description.equals("")){
                        message += main + ": " + description + "\r\n";
                    }
                }

                message += humidity;

                if(!message.equals("")){
                    weatherDescription.setText(message);
                    weatherTemp.setText(temperature);
                    //textView.setMovementMethod(new ScrollingMovementMethod());
                }

            } catch (Exception e) {
                weatherTemp.setText("");
                weatherDescription.setText("Please Check your Spelling!");
            }
        }
    }

    public void getWeather(){
        try {
            DownloadTask task = new DownloadTask();

            String encodedCityName = URLEncoder.encode(weatherCityName.getText().toString(), "UTF-8");


            task.execute("https://openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=b6907d289e10d714a6e88b30761fae22");

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(weatherCityName.getWindowToken(), 0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void locationUpdater() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient = new FusedLocationProviderClient(this);
            locationRequest = new LocationRequest();


            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    getAddress(locationResult);
                }
            }, getMainLooper());
        }


    }
    public void getAddress(LocationResult location){
        String address = "Could Not Find Your Location";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLastLocation().getLatitude(), location.getLastLocation().getLongitude(), 1);

            if(listAddresses.size()>0){
                address = listAddresses.get(0).getLocality();
                weatherCityName.setText(address);
                getWeather();
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        weatherTemp.setText("");
        weatherDescription.setText("NO INTERNET\nCONNECTION");
        return false;
    }

}
