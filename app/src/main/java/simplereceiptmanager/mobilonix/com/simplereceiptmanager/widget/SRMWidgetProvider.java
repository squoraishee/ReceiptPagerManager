package simplereceiptmanager.mobilonix.com.simplereceiptmanager.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.ReceiptGridActivity;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

public class SRMWidgetProvider extends AppWidgetProvider {

    private static final String MyOnClick = "myOnClickTag";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        for(int i=0; i<appWidgetIds.length; i++){
            int currentWidgetId = appWidgetIds[i];
            String url = "http://www.tutorialspoint.com";

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent intent = new Intent(context, ReceiptGridActivity.class);
            intent.setData(Uri.parse(url));
            PendingIntent pending = PendingIntent.getActivity(context, 0,intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
            views.setOnClickPendingIntent(R.id.button, pending);

            appWidgetManager.updateAppWidget(currentWidgetId, views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        GlobalUtil.toast("Widget was accessed!");

        if(MyOnClick.equals(intent.getAction())) {
            //openDialog(context);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void openDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Test Widget?");
        builder.setMessage("Would you like Test the widtdget?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
