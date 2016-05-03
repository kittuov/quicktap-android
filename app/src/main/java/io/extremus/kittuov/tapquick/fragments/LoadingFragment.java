package io.extremus.kittuov.tapquick.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.extremus.kittuov.tapquick.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {


    public static boolean error = false;

    public LoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void LoadError() {
        error = true;
        Log.d("Load", "load Error");
        View content = getView();
        if (content == null) return;
        View d = content.findViewById(R.id.loading_spinner);
        if (d == null) return;
        d.setVisibility(View.GONE);
        View v = content.findViewById(R.id.error_layout);
        if (v == null) return;
        v.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (error){
            LoadError();
        }
    }

    public void Loading() {
        error = false;
        Log.d("Load", "loading");

        View content = getView();
        if (content == null) return;
        View d = content.findViewById(R.id.loading_spinner);
        if (d == null) return;
        d.setVisibility(View.VISIBLE);
        View v = content.findViewById(R.id.error_layout);
        if (v == null) return;
        v.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

}
