package io.extremus.kittuov.tapquick.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import io.extremus.kittuov.tapquick.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDashFragment extends Fragment {


    public GameDashFragment() {
        // Required empty public constructor
    }
    public String roomName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_dash, container, false);
        int won = getActivity().getIntent().getIntExtra("won", 0);
        int total = getActivity().getIntent().getIntExtra("total", 0);

        TextView won_view = (TextView) v.findViewById(R.id.won_text);
        TextView total_view = (TextView) v.findViewById(R.id.total_text);

        if (won_view != null && total_view != null) {
            won_view.setText("Won: "+String.valueOf(won));
            total_view.setText("Total: "+String.valueOf(total));
        }

        return v;
    }

    public boolean validate () {
        View v = getView();
        assert v != null;
        EditText room_view = (EditText) v.findViewById(R.id.room_input);
        this.roomName = String.valueOf(room_view.getText());
        return room_view.getText().length() > 0;
    }

}
