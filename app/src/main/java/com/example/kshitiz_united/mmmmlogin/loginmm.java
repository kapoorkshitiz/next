package com.example.nova.loginmm;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load Brunel Website using WebView. Enable JavaScript so we can use JavaScript functions to click/enter text.
        final WebView myWebView = (WebView) findViewById(R.id.mWeb);
        myWebView.loadUrl("https://teaching.brunel.ac.uk/SWS-1617/login.aspx");
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);

        myWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView webView, String url) {
            }
        });

        //Initialise Login Button and TextBoxes.
        final Button loginButton       = (Button)findViewById(R.id.button);
        final EditText userBox   = (EditText)findViewById(R.id.userbox);
        final EditText passbox   = (EditText)findViewById(R.id.passbox);

        loginButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(final View view)
                    {
                        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
                        loginButton.setEnabled(false);

                        myWebView.loadUrl("javascript:(function(){document.getElementById('tUserName').value = '"+ userBox.getText()+"';})()");
                        myWebView.loadUrl("javascript:(function(){document.getElementById('tPassword').value = '"+ passbox.getText()+"';})()");
                        myWebView.loadUrl("javascript:(function(){document.getElementById('bLogin').click();})()");

                        //Delay for the page to load
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //This code will be executed after 2 seconds
                                TextView t = (TextView)findViewById(R.id.textView);
                                if (myWebView.getUrl().equals("https://teaching.brunel.ac.uk/SWS-1617/default.aspx")){
                                    //We logged in successfully
                                    t.setText("Successful Login");
                                    t.setTextColor(Color.rgb(42, 122, 65));
                                    findViewById(R.id.textView).setVisibility(View.VISIBLE);
                                    //Reset program
                                    myWebView.loadUrl("https://teaching.brunel.ac.uk/SWS-1617/login.aspx");
                                } else {
                                    //We failed to login
                                    t.setText("Invalid Username or Password");
                                    t.setTextColor(Color.rgb(145, 23, 23));
                                    findViewById(R.id.textView).setVisibility(View.VISIBLE);
                                }
                                loginButton.setEnabled(true);
                            }
                        }, 2000);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}