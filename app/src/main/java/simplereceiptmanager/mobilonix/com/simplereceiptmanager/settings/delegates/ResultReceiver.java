package simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.delegates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

public class ResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        GlobalUtil.toast("Received update!");
    }
}
