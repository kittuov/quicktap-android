package io.extremus.kittuov.tapquick.fragments.game;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.extremus.kittuov.tapquick.MainActivity;
import io.extremus.kittuov.tapquick.R;
import io.extremus.kittuov.tapquick.utils.RoomUsersAdapter;
import io.extremus.kittuov.tapquick.utils.WebSocket;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameRoomFragment extends Fragment {
    private RoomUsersAdapter usersAdapter;
    private JSONArray users;
    private String room;

    public GameRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_room, container, false);
        try{
            ViewGroup parent = (ViewGroup) v.findViewById(R.id.room_container);
            usersAdapter = new RoomUsersAdapter(parent,getActivity());
            GridView view = (GridView) v.findViewById(R.id.userGrid);
            this.usersAdapter.setUsers(users);
            view.setAdapter(usersAdapter);
            TextView t = (TextView) v.findViewById(R.id.room_name);
            t.setText(room);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return v;
    }

    public void setUsers(JSONArray users) {
        this.users=users;
        if (this.usersAdapter!=null){
            this.usersAdapter.setUsers(users);
        }
    }

    public void add_user(JSONObject user){
        final JSONObject fUser = user;
        if (this.usersAdapter!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameRoomFragment.this.usersAdapter.addUser(fUser);
                }
            });
        }
        else{
            Log.e("GameRoomFragment", "added user before initializing users adapter!");
        }
    }

    public void remove_user (int user){
        final int fUser = user;

        if (this.usersAdapter!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameRoomFragment.this.usersAdapter.removeUser(fUser);
                }
            });
        }
        else{
            Log.e("GameRoomFragment", "removed user before initializing users adapter!");
        }
    }

    @Override
    public void onDetach() {
        Log.d("GameRoomFrag", "detatched");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.mWebView.loadUrl("javascript:leaveRoom();");
            }
        });
        super.onDetach();
    }

    public void setRoom(String room) {
        this.room = room;
        View v = getView();
        if (v!=null){
            TextView t = (TextView) v.findViewById(R.id.room_name);
            t.setText(room);
        }
    }
}
