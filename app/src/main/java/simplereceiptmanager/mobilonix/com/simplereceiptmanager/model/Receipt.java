package simplereceiptmanager.mobilonix.com.simplereceiptmanager.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.R;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.SRMApplication;
import simplereceiptmanager.mobilonix.com.simplereceiptmanager.util.GlobalUtil;

public class Receipt implements Serializable {

    public static String EMPTY = "EMPTY";

    /* Sentinel Value to make sure we can return something in the case of a null state */
    public static Receipt NO_ITEM = new Receipt(null, EMPTY);

    String receiptImageUri;
    String receiptDate;

    public Receipt(Uri receiptImageUri, String receiptDate) {
        this.receiptDate = receiptDate;

        if(receiptImageUri != null) {
            this.receiptImageUri = receiptImageUri.toString();
        } else {
            this.receiptImageUri = GlobalUtil.getImageUri(SRMApplication.getInstance(),
                    BitmapFactory.decodeResource(SRMApplication.getInstance().getResources(),
                            R.drawable.smiley_face)).toString();
        }

        /**
         * TODO:
         * Consider sennding date valyesl
         */
    }

    public Uri getReceiptImageUri() {
        return Uri.parse(receiptImageUri);
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public void setReceiptImageUri(Uri imageUri) {
        this.receiptImageUri = imageUri.toString();
    }

}
