package vaas.autocurehome.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import vaas.autocurehome.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    String token = "";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sharedPreferences = getSharedPreferences("TML", MODE_PRIVATE);

        try {
            token = InstanceID.getInstance(this)
                    .getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "GCM Registration Token: " + token);

            sharedPreferences.edit().putBoolean("token_created", true).apply();
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration
            // data on a third-party server, this ensures that we'll attempt the update at a later
            // time.
            sharedPreferences.edit().putBoolean("token_created", false).apply();
        }
    }

}
