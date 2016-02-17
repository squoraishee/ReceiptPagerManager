package simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.notification.NotificationService;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

/**
 * A notification should actually open this
 */
public class ReceiptGridActivity extends AppCompatActivity {

    /**
     * SO this is a bound service, which means android acitieites and other services should be able to send requests to it an dreceive rreuests from it
     * How can you do IPC in andrdoi
     * <p/>
     * SO basiclay ohter servies acna interact with the service and bind to it. so the questions is how do you bound to a services
     */

    public static final String SERVICE_NAME = NotificationService.class.getPackage().toString();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* QUESTION TO ANSWER: How does saved instance state work */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_grid);

        /* Services must be started through intents */
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
        bindToGridNotificationService();
    }

    /**
     * Check running services on the OS already
     *
     * @return
     */
    private boolean isNotificationServiceRunning(new GlobalUtil.Callback<T> {

    })
    @Override
    public void onPause() {

        stopService(new Intent(this, NotificationService.class));
        super.onPause();
    }

    @Override
    public void onDestroy() {

        stopService(new Intent(this, NotificationService.class));
        super.onDestroy();
    }

    private void bindToGridNotificationService() {
        /**
         * Notice all bind services related flags use the BIND prefix
         * I tlooks like all the bound services proefis relate to the imposrtance of the acitivyt itself and the relative importanc eof ht e serivce
         * so basicllay based on how important the service is the acitivyt will have relaitiyhigh importance.
         *
         * Ho wis process importance managed inandroid?
         * SO if someoen tries to bind to the service, the auto create flag will be passe over to the serice.
         *
         * Ifhtis means hwne the service is abound to the autocreate flag will be called will caause the service to be cretaed..
         *
         * In the case of a bound service you don't have to manage th elifeyycle of an axtiicty then in what cases do you have to maager the lufecycle of an actity
         *
         * SO the quesiton is do i want the service crated automatically?  It probabaly doesn't make sense if it isn't existant already
         *
         * so i guess what its tyring to say for startcommand its not undindevicded ito unbind the service
         * */

        bindService(new Intent(), notificationServiceConnection, BIND_ADJUST_WITH_ACTIVITY);
    }

    ServiceConnection notificationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GlobalUtil.toast("There is an existing connection now between the service and grid notification activity!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            GlobalUtil.toast("Grid activity was disconnected from the service!");
        }
    };

}
