package io.extremus.kittuov.tapquick;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import io.extremus.kittuov.tapquick.fragments.GameDashFragment;
import io.extremus.kittuov.tapquick.fragments.game.GameRoomFragment;
import io.extremus.kittuov.tapquick.utils.ImageAdapter;
import io.extremus.kittuov.tapquick.utils.PageLoadListner;
import io.extremus.kittuov.tapquick.utils.RoomUsersAdapter;
import io.extremus.kittuov.tapquick.utils.WebSocket;

public class GameActivity extends AppCompatActivity {

    public static String WS_URL = "ws://10.0.2.2:8001/room/";
//    public static final String WS_URL = "ws://10.102.35.24:8001/room/";

    public static final int DASHBOARD = 0;
    public static final int ROOM = 1;
    public static final int GAME = 0;

    public int currentState;

    private GameDashFragment gameDash;
    private WebSocket webSocket;
    private GameRoomFragment gRoomFrag;

    public static void MakeURL(String server){
        WS_URL = "ws://"+server+"/room/";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = this.getSharedPreferences("io.extremus.kittuov.tapquick.SETTINGS_KEY", Context.MODE_PRIVATE);
        String server = pref.getString("WSSERVER", "10.0.2.2:8001");
        MakeURL(server);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String fName = getIntent().getStringExtra("firstName");
        String lName = getIntent().getStringExtra("lastName");
        int image = getIntent().getIntExtra("image", 0);
        int imageRes = ImageAdapter.mThumbIds[image];

        ImageView view_avatar = (ImageView) findViewById(R.id.avatar);
        assert view_avatar != null;
        view_avatar.setImageResource(imageRes);
        TextView view_fName = (TextView) findViewById(R.id.FirstName);
        TextView view_lName = (TextView) findViewById(R.id.LastName);
        if (view_fName != null) {
            view_fName.setText(fName);
        }
        if (view_lName != null) {
            view_lName.setText(lName);
        }
        gameDash = new GameDashFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.game_container, gameDash, "gameDash")
                .commit();
        currentState = DASHBOARD;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    public void joinRoom(View v) {
        joinRoom();
    }

    public void joinRoom() {
        if (gameDash.validate()) {
            webSocket = new WebSocket(MainActivity.mWebView) {
                @Override
                public void OnMessage(String data) {
                    Log.d("Ws Message", data);
                    try {
                        JSONObject item = new JSONObject(data);
                        if (item.has("room")) {
                            JSONArray users = item.getJSONArray("users");
                            gRoomFrag = new GameRoomFragment();
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.animator.slide_from_right,
                                            R.animator.slide_right,
                                            R.animator.slide_from_left,
                                            R.animator.slide_left)
                                    .replace(R.id.game_container, gRoomFrag, "gameRoom")
                                    .addToBackStack(null)
                                    .commit();
                            gRoomFrag.setUsers(users);
                            gRoomFrag.setRoom(item.getString("room"));
                        }
                        if (item.has("add_user")){
                            JSONObject user = item.getJSONObject("add_user");
                            gRoomFrag.add_user(user);
                        }
                        if (item.has("remove_user")){
                            int user = item.getInt("remove_user");
                            gRoomFrag.remove_user(user);
                        }

                    } catch (JSONException e) {
                        Log.e("Ws Message", "unable to parse '" + data + "'");
                    }
                }

                @Override
                public void OnDisconnect() {
                    Log.d("Ws disconnect", "disconnect");
                    getFragmentManager().popBackStack();
                }
            };
            webSocket.joinRoom(WS_URL, gameDash.roomName);
        } else {
            Toast.makeText(this, "Enter a room name", Toast.LENGTH_SHORT).show();
        }
    }

    public void logout(){
        MainActivity.jsInterface.setPageLoadListener(new PageLoadListner() {
            @Override
            public void onPageFinished(String url, int status, String response) {
                if (status!=200){
                    Toast.makeText(GameActivity.this, "Cannot send request to the server",Toast.LENGTH_SHORT).show();
                }else{
                    Intent mainActivity = new Intent(GameActivity.this,MainActivity.class);
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.mWebView.destroy();                        }
                    });

//                    MainActivity.mWebView= null;
                    MainActivity.jsInterface = null;
                    startActivity(mainActivity);
                    GameActivity.this.finish();
                }
            }
        });

        GameActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.mWebView.loadUrl("javascript:request('GET','/api/logout/',{});");
            }
        });
    }

    public void logout(View v){
        logout();
    }

    public void startGame(){
        
    }

    public void startGame(View v){
        startGame();
    }
}
