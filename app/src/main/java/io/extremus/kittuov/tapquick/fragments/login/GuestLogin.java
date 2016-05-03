package io.extremus.kittuov.tapquick.fragments.login;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.extremus.kittuov.tapquick.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestLogin extends Fragment {

    public static String FirstName;
    public static String LastName;

    public GuestLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_login, container, false);
    }

    public void validate(){
        View v = getView();
        assert v != null;
        EditText firstName = (EditText) v.findViewById(R.id.firstName);
        EditText lastName = (EditText) v.findViewById(R.id.lastName);

        GuestLogin.FirstName = firstName.getText().toString();
        GuestLogin.LastName = lastName.getText().toString();
    }

}
