package io.extremus.kittuov.tapquick;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.extremus.kittuov.tapquick.fragments.LoadingFragment;
import io.extremus.kittuov.tapquick.fragments.Login;
import io.extremus.kittuov.tapquick.fragments.login.AvatarChoice;
import io.extremus.kittuov.tapquick.fragments.login.GuestLogin;
import io.extremus.kittuov.tapquick.utils.AndroidJSInterface;
import io.extremus.kittuov.tapquick.utils.PageLoadListner;

public class MainActivity extends AppCompatActivity {
    public static String URL = "http://10.0.2.2" +
            ":8000/android/";

    public static WebView mWebView;

    public LoadingFragment loadingFrag;
    public Fragment avatarFrag;
    public static AndroidJSInterface jsInterface;

    public static void MakeURL(String server){
        URL = "http://"+server+"/android/";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = this.getSharedPreferences("io.extremus.kittuov.tapquick.SETTINGS_KEY", Context.MODE_PRIVATE);
        String server = pref.getString("HTTPSERVER","10.0.2.2:8000");
        MakeURL(server);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            this.loadingFrag = new LoadingFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, loadingFrag, "loadingFrag")
                    .commit();
            mWebView = null;
        }
        if (savedInstanceState != null) {
            loadingFrag = (LoadingFragment) getFragmentManager().findFragmentByTag("loadingFrag");
            MainActivity.mWebView.setWebViewClient(new WebViewClient() {

                Handler timeout;

                boolean timedout;

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    timedout = true;
                    timeout = new Handler();
                    timeout.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (timedout) {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingFrag.LoadError();
                                    }
                                });
                            }

                        }
                    }, 4000);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    timedout = false;
                    loadingFrag.LoadError();
                }
            });
        }
        Log.d("shit", String.valueOf(LoadingFragment.error));
        if (mWebView == null) {
            mWebView = new WebView(getApplicationContext());
            mWebView.getSettings().setJavaScriptEnabled(true);
            jsInterface = new AndroidJSInterface();
            jsInterface.setPageLoadListener(new PageLoadListner() {
                @Override
                public void onPageFinished(String url, int status, String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:user();");
                        }
                    });
                    jsInterface.setPageLoadListener(new PageLoadListner() {
                        @Override
                        public void onPageFinished(String url, int status, String response) {
                            if (status != 200) {
                                getFragmentManager().beginTransaction()
                                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                                        .replace(R.id.container, new Login())
                                        .commit();
                            }else{
                                openGame(response);
                            }
                        }
                    });
                }
            });
            mWebView.addJavascriptInterface(jsInterface, "Android");
            MainActivity.mWebView.setWebViewClient(new WebViewClient() {

                Handler timeout;

                boolean timedout;

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    timedout = true;
                    Log.d("webLoading",url);
                    timeout = new Handler();
                    timeout.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (timedout) {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingFrag.LoadError();
                                    }
                                });
                            }

                        }
                    }, 4000);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    timedout = false;
                    loadingFrag.LoadError();
                }
            });
            mWebView.loadUrl(URL);
            Log.d("MainWebView", "initialization success");
        }
    }

    // ment for loading initial page only!
    public void pageLoadRetry(View v) {
        pageLoadRetry();
    }

    public void pageLoadRetry() {
        this.loadingFrag.Loading();
        mWebView.loadUrl(URL);
    }

    // open Guest login from options!
    public void guestLogin(View v) {
        guestLogin();
    }

    public void guestLogin() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_from_right,
                        R.animator.slide_right,
                        R.animator.slide_from_left,
                        R.animator.slide_left)
                .replace(R.id.container, new GuestLogin(), "guestLogin")
                .addToBackStack(null)
                .commit();
        avatarFrag = new AvatarChoice();
    }

    public void back(View v) {
        back();
    }

    public void back() {
        getFragmentManager().popBackStack();
    }

    // can be called only when you are currently in guest login field and fill first name and lastname
    public void avatarSelect() {
        GuestLogin frag = (GuestLogin) getFragmentManager().findFragmentByTag("guestLogin");
        frag.validate();
        if (GuestLogin.FirstName == null || Objects.equals(GuestLogin.FirstName, "") || GuestLogin.LastName == null || Objects.equals(GuestLogin.LastName, "")) {
            Toast.makeText(this, "Fill all the Columns", Toast.LENGTH_SHORT).show();
            return;
        }
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_from_right,
                        R.animator.slide_right,
                        R.animator.slide_from_left,
                        R.animator.slide_left)
                .replace(R.id.container, avatarFrag)
                .addToBackStack("avatar")
                .commit();
    }

    public void avatarSelect(View v) {
        avatarSelect();
    }

    // attempts a guestLogin if succeeds opens game activity else asks to retry
    public void atmptGuestLogin(){
        if (AvatarChoice.selected < 0 ){
            Toast.makeText(this,"Pick an Avatar", Toast.LENGTH_SHORT).show();
            return;
        }
        final String url = "/api/glogin/";
        JSONObject object = new JSONObject();
        try {
            object.put("firstName",GuestLogin.FirstName);
            object.put("lastName",GuestLogin.LastName);
            object.put("image",AvatarChoice.selected);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String datastr = object.toString();
        Log.d("dataStr",datastr);
        jsInterface.setPageLoadListener(new PageLoadListner() {
            @Override
            public void onPageFinished(String url, int status, String response) {
                if (status!=200){
                    Toast.makeText(MainActivity.this,"Error: "+String.valueOf(status),Toast.LENGTH_SHORT).show();
                }else{
                    openGame(response);
                }
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:request('POST','"+url+"',"+datastr+");");
            }
        });
    }

    public void atmptGuestLogin(View v){
        atmptGuestLogin();
    }


    public void openGame(String httpResponse){
        JSONObject object;
        try {
            object = new JSONObject(httpResponse);
            Intent i = new Intent();
            i.putExtra("firstName",object.getString("firstName"));
            i.putExtra("lastName",object.getString("lastName"));
            i.putExtra("image",object.getInt("image"));
            i.putExtra("won",object.getInt("won"));
            i.putExtra("total",object.getInt("total"));
            i.setAction("io.extremus.kittuov.tapquick.GAME");
            startActivity(i);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Server Error Please restart App",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else back();
    }

    public void openSettings(){
        Intent i = new Intent("io.extremus.kittuov.tapquick.SETTINGS");
        startActivity(i);
    }
    public void openSettings(View v){
        openSettings();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.settings){
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }
}
