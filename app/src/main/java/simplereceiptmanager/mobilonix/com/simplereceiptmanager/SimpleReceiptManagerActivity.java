package simplereceiptmanager.mobilonix.com.simplereceiptmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.model.Receipt;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.session.Session;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.ExistingReceiptFragment;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.NewReceiptFragment;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.ReceiptGridActivity;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.ui.dynamic.ZoomOutPageTransformer;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.IntentHelper;

public class SimpleReceiptManagerActivity extends AppCompatActivity {

    /* The requirements of simple receipt manager are as follows */
    //1.) You have a view pager that lets you scroll to the end of recipt list, or a button
    //whos puprose is to make a new recipet.  On the receipt view, you can take a picture and populate
    //the receipt for later use

    //SO for the fragment pager you simply use the basi view pager
    ViewPager receiptPager;
    ReceiptPagerAdapter receiptPagerAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_receipt_manager);

        Session.INSTANCE.addReceipt(new Receipt(null, Receipt.EMPTY));
        Session.INSTANCE.addReceipt(new Receipt(null, Receipt.EMPTY));
        Session.INSTANCE.addReceipt(new Receipt(null, Receipt.EMPTY));

        initUI();
    }

    public void initUI() {
        receiptPager = (ViewPager)findViewById(R.id.receipt_view_pager);
        toolbar = (Toolbar)findViewById(R.id.receipt_manager_toolbar);
        setActionBar(toolbar);

        receiptPagerAdapter = new ReceiptPagerAdapter(getSupportFragmentManager(),
                Session.INSTANCE.getReceiptList());
        receiptPager.setAdapter(receiptPagerAdapter);
        receiptPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public void removeItem(int position) {
        Session.INSTANCE.removeReceipt(position);
        receiptPagerAdapter.notifyDataSetChanged();
        receiptPager.setCurrentItem(0, true);
    }

    public void fireNotification(Receipt receipt) {

        if(receipt.equals(Receipt.NO_ITEM)) {
            return;
        }

        /* The notification itself will have an intent associated with it.  This intent in this case
         * will open up the ReceiptGrid activity.  For pending intents, you can usually write the intent
          * with pending intent as a pair */
        Intent intent = new Intent(this, ReceiptGridActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        /* The remote views class is apparently used for displaying a view view hierarchy in another process.
        * How can you use this to interact with your acitivity*/
        RemoteViews notificationView = new RemoteViews(getPackageName(),
                R.layout.notification_layout);


        /* How many other ways of creating custom notification is there? */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.smiley_face)
                .setTicker("You have a new receipt added to yo ur collection!")
                .setAutoCancel(true)
                .setContentTitle("Got a new Receipt Message taken on " + GlobalUtil.getDate())
                .setContentIntent(notificationIntent)
                .setContent(notificationView);

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());
    }

    @NotNull
    public void createNewReceipt(Bitmap data) {
        Session.INSTANCE.addReceipt(new Receipt(
                GlobalUtil.getImageUri(this, data),
                GlobalUtil.getDate()));
        receiptPagerAdapter.notifyDataSetChanged();
        receiptPager.setCurrentItem(receiptPager.getAdapter().getCount() - 1, true);

        fireNotification(Session.INSTANCE.getLastReceipt());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* The menu inflater basically comes from the activity in the otpions menu.  The menu resource gets
        * inflated into the menu object*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        /* Returning true here is the important required piece to get this menu displaying something */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.option: {
                IntentHelper.openSettingsActivity(this);
                break;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        createNewReceipt(GlobalUtil.rotateImage((Bitmap) data.getExtras().get("data"), -90));
    }

    /*Keep the pager adapter as a static class*/
    public static class ReceiptPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Receipt> receiptArray;
        private long baseId = 0;

        /* Lust like getView, getItem populates a fragment when the item is switched.  You also need
         * to pass the appropriate fragment manager to the receipt view pager */
        public ReceiptPagerAdapter(FragmentManager fm, ArrayList<Receipt> receiptArray) {
            super(fm);
            GlobalUtil.toast("Instantiated view pager");
            this.receiptArray = receiptArray;
        }

        @Override
        public int getCount() {
            return receiptArray.size() + 1;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return NewReceiptFragment.getInstance();
            } else {
                return ExistingReceiptFragment.newInstance(receiptArray.get(position - 1), position - 1);
            }
        }

        @Override
        public int getItemPosition(Object object) {

            /* This will ensure fragments are removed from memory */
            return PagerAdapter.POSITION_NONE;
        }
    }


}
