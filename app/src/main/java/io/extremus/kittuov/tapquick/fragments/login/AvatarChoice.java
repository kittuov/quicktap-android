package io.extremus.kittuov.tapquick.fragments.login;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import io.extremus.kittuov.tapquick.R;
import io.extremus.kittuov.tapquick.utils.ImageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarChoice extends Fragment {

    public static int selected = -1;
    public AvatarChoice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_avatar_choice, container, false);
        assert v != null;
        GridView g = (GridView) v.findViewById(R.id.avatarGrid);
        g.setAdapter(new ImageAdapter(getActivity()));
        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int count = parent.getAdapter().getCount();
                for (int i=0;i<count;i++){
                    parent.getChildAt(i).setBackground(null);
                }
                selected = position;
                view.setBackground(getResources().getDrawable(R.drawable.round_rect));
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
