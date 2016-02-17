package simplereceiptmanager.mobilonix.com.simplereceiptmanager.notification;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

public class NotificationService extends Service {


    public final static String BROADCAST_ACTION = "ServiceBroadcastAction";
    private boolean running = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        /* What does the onBind in a service do? YOu need to handle key aspects of hte service lifecycle.  What key aspects of the service
        * lifecycle do we need to be onetened about  What os th andorid service sifecycle
        * What is the difference between a boudned an anunvbounded service.  So basicllay onstar command is called when another comcpoennt calle the services
        * Services are unrning in the same process by daefual becut canyou scase a seice to run in a separate andorid process?*/

        GlobalUtil.toast("The service has been bound to by the first client.  This will only be called for the first guy bound to it." +
                "All additional bindings will work but won't call this method.");
        return null;
    }

    @Override
    public void onCreate() {

        IntentFilter localBroadcastFilter = new IntentFilter();
        localBroadcastFilter.addAction(BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                GlobalUtil.toast("Notification Service: Receiver broadcast action!");
            }
        }, localBroadcastFilter);
    }

    /**
     * So basically start command is called instead of onstart for a service.  What is onStart sticky and start sticky compaitbility
     * What is return sticky
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        GlobalUtil.toast("Someone started the service!");
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        if(!running) {
            running = true;
        }

        //SO if i don't ave this is as stickey what sthe dierenc ebetween strated this astickey and not sticky
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        running = false;
        sendBroadcast(new Intent());
        super.onDestroy();
    }


    public boolean isServiceRunning() {
        return running;
    }

}
