package vaas.autocurehome.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vaas.autocurehome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoteControlFraagment extends Fragment {


    public RemoteControlFraagment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remote_control_fraagment, container, false);
    }

}
