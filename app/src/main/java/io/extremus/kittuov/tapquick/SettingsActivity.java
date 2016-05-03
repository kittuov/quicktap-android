package io.extremus.kittuov.tapquick;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void save(View v){
        EditText http_view = (EditText) findViewById(R.id.http_server);
        EditText ws_view = (EditText) findViewById(R.id.ws_server);

        if (http_view!=null || ws_view!=null){
            SharedPreferences pref = this.getSharedPreferences("io.extremus.kittuov.tapquick.SETTINGS_KEY", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("HTTPSERVER",http_view.getText().toString());
            editor.putString("WSSERVER",ws_view.getText().toString());
            MainActivity.MakeURL(http_view.getText().toString());
            GameActivity.MakeURL(ws_view.getText().toString());

            editor.apply();
        }
        this.onBackPressed();
    }

    public void back(View v){
        onBackPressed();
    }
}
