package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    WebView webView;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        String url = "http://results.iul.ac.in/even/EvenRollInput.aspx?course=BTechCSE4Sem";

        final String js = "javascript:" +
                "var x = document.getElementById('txtRollNo').value='"+username+"';" +
                "var y = document.getElementById('TextBox2').value='rrr';" +
                "var form1 = document.getElementById('btnSubmit');" +
                "form1.click();";


        webView.loadUrl(url);

        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
                //super.onPageFinished(view, url);
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

    }
}
