package simplereceiptmanager.mobilonix.com.simplereceiptmanager.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.settings.ui.SettingsActivity;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.ReceiptGridActivity;

public class IntentHelper {

    public static void openSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public static void shareReceipt(Context context,
                                    String subject,
                                    String emailAddress,
                                    String body) {
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        /* Here it seems like i'd be able to perform a broadcast when the share action is completed */
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, int)
        context.startActivity(Intent.createChooser(intent, "How do you want to share?"));
    }

    public static void startGridActivity(Context context) {
        Intent intent = new Intent(context, ReceiptGridActivity.class);
        context.startActivity(intent);
    }

}
