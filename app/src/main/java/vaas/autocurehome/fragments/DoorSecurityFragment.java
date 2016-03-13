package vaas.autocurehome.fragments;


import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import vaas.autocurehome.R;
import vaas.autocurehome.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorSecurityFragment extends Fragment {

    SharedPreferences sharedPreferences;

    ImageView lockImage;
    TextView securityStatus, status;

    ProgressDialog progressDialog;

    public DoorSecurityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_door_security, container, false);

        AppCompatButton refresh = (AppCompatButton) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckStatusInBackground(view).execute();
            }
        });

        new CheckStatusInBackground(view).execute();

        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setRepeatCount(ValueAnimator.INFINITE);
        shimmerFrameLayout.setDuration(2500);
        shimmerFrameLayout.setRepeatDelay(0);
        shimmerFrameLayout.startShimmerAnimation();

        return view;
    }

    private class CheckStatusInBackground extends AsyncTask<Void, Void, Void> {

        View view;
        public CheckStatusInBackground(View view) {
            this.view = view;
        }
        @Override
        protected void onPreExecute() {
            sharedPreferences = getActivity().getSharedPreferences("Autocure", Context.MODE_PRIVATE);
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Updating status");
            lockImage = (ImageView) view.findViewById(R.id.lock);
            securityStatus = (TextView) view.findViewById(R.id.security_status);
            status = (TextView) view.findViewById(R.id.status);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            String lockValue = sharedPreferences.getString("lock_status", "");
            lockValue = lockValue.trim();

            if (lockValue.equals("1")) {
                Log.d("AH", "aaya andar");
                lockImage.setImageResource(R.drawable.unlocked);
                securityStatus.setText("Your home is not secure!");
                status.setText("Unlocked");
                status.setTextColor(Color.RED);
            } else if (lockValue.equals("0")) {
                Log.d("AH", "aaya andar");
                lockImage.setImageResource(R.drawable.locked);
                securityStatus.setText("Your home is secure!");
                status.setText("Locked");
                status.setTextColor(Color.GREEN);
            }

            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Utils(getActivity()).readStatus("door");
            return null;
        }
    }

}
