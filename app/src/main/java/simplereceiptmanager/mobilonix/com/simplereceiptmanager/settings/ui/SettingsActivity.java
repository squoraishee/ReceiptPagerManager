package simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.SRMApplication;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.delegates.ResultReceiver;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.model.SettingsFunction;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.model.SettingsOption;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends AppCompatActivity {

    FrameLayout storageOptionsFrame;
    FrameLayout specialOptionsFrame;
    FrameLayout debugOptionsFrame;

    ArrayList storageOptions;
    ArrayList specialOptions;
    ArrayList debugOptions;

    Button testBroadcastButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);

        initUI();

        storageOptions = new ArrayList();
        specialOptions = new ArrayList();
        debugOptions = new ArrayList();

        initOptions();

        initOptionsUi(storageOptions, storageOptionsFrame);
        initOptionsUi(specialOptions, specialOptionsFrame);
        initOptionsUi(debugOptions, debugOptionsFrame);
    }

    private void initUI() {
        storageOptionsFrame = (FrameLayout)findViewById(R.id.storage_options_frame);
        specialOptionsFrame = (FrameLayout)findViewById(R.id.special_options_frame);
        debugOptionsFrame = (FrameLayout)findViewById(R.id.debug_options_frame);

        handleDebugUI();
    }

    /* Need to refactor these options to come more from configuration if possible */
    private void initOptions() {

        storageOptions.add(new SettingsOption("Store Images Locally",
                SettingsOption.OptionType.CHECK, new SettingsFunction() {
            @Override
            public void execute(boolean state) {
                if(state) {
                    GlobalUtil.toast("State: " + state);
                }
            }
        }));

        specialOptions.add(new SettingsOption("Store Images Locally",
                SettingsOption.OptionType.TOGGLE, new SettingsFunction() {
            @Override
            public void execute(boolean state) {
                if(state) {
                    GlobalUtil.toast("State: " + state);
                }
            }
        }));

        specialOptions.add(new SettingsOption("Send Notifications",
                SettingsOption.OptionType.TOGGLE, new SettingsFunction() {
            @Override
            public void execute(boolean state) {
                if(state) {
                    GlobalUtil.toast("State: " + state);
                }
            }
        }));

        debugOptions.add(new SettingsOption("Store Images Locally",
                SettingsOption.OptionType.CHECK, new SettingsFunction() {
            @Override
            public void execute(boolean state) {
                if (state) {
                    GlobalUtil.toast("State: " + state);
                }
            }
        }));
    }

    private void initOptionsUi(ArrayList<SettingsOption> options, FrameLayout optionsFrame) {

        SettingsFragmentSection storageOptionsFragment = SettingsFragmentSection.newInstance(options);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(optionsFrame.getId(), storageOptionsFragment);
        transaction.commit();

    }

    private void handleDebugUI() {
        testBroadcastButton = (Button)findViewById(R.id.test_broadcast_button);
        testBroadcastButton.setVisibility(View.GONE);
        testBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SRMApplication.getInstance(), ResultReceiver.class);
                intent.setAction(getString(R.string.broadcast_action));
                sendBroadcast(intent);
            }
        });
    }

    /* This is needed for the calligraphy library in the library where you want to install custo,
     * fonts too. */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
