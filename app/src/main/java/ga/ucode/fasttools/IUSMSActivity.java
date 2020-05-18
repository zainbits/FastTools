package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class IUSMSActivity extends AppCompatActivity {

    WebView webView;
    String enrollment,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iusms);

        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        enrollment = intent.getStringExtra("enroll");
        password = intent.getStringExtra("pass");

        String url = "http://sms.iul.ac.in/Student/login.aspx";

        final String js = "javascript:" +
                "var x = document.getElementById('txtun').value='"+enrollment+"';" +
                "var y = document.getElementById('txtpass').value='"+password+"';" +
                "var form1 = document.getElementById('btnlog');" +
                "form1.click();";

        webView.loadUrl(url);

        Toast.makeText(this, enrollment, Toast.LENGTH_SHORT).show();

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
    }
}
