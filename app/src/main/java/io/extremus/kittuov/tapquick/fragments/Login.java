package io.extremus.kittuov.tapquick.fragments;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.extremus.kittuov.tapquick.MainActivity;
import io.extremus.kittuov.tapquick.R;
import io.extremus.kittuov.tapquick.fragments.login.GuestLogin;

public class Login extends Fragment {

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        return v;
    }
}
