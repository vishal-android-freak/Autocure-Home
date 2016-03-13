package vaas.autocurehome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import vaas.autocurehome.fragments.AppliancesStatusFragment;
import vaas.autocurehome.fragments.DoorSecurityFragment;
import vaas.autocurehome.fragments.RemoteControlFraagment;
import vaas.autocurehome.services.RegistrationIntentService;
import vaas.autocurehome.utils.ViewPageAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    int[] tabIcons = { R.mipmap.ic_lock_white_24dp, R.mipmap.ic_report_problem_white_24dp, R.mipmap.ic_settings_remote_white_24dp };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        registerClient();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFrag(new DoorSecurityFragment(), "Door Security");
        adapter.addFrag(new AppliancesStatusFragment(), "Appliances' Status");
        adapter.addFrag(new RemoteControlFraagment(), "Auto Remote");
        viewPager.setAdapter(adapter);
    }

    public void registerClient() {
        // Get the sender ID
        String senderId = getString(R.string.gcm_defaultSenderId);
        if (!("".equals(senderId))) {

            // Register with GCM
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
}
