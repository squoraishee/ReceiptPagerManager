package simplereceiptmanager.mobilonix.com.simplereceiptmanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import simplereceiptmanager.mobilonix.com.simplereceiptmanager.SRMApplication;

/**
 * Created by quoraiss on 2/5/16.
 */
public class GlobalUtil {

    /**
     * A simple toast method
     *
     * @param message
     */
    public static void toast(String message) {
        Toast.makeText(SRMApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @return
     */
    public static String getDate() {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String date = day + "/" + month + "/" + year;

        return date;
    }

    /**
     *
     * @param context
     * @param inImage
     * @return
     */
    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    /**
     * Rotate an image to fit the canvas of a portrait mode phone
     *
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    public interface Callback<T> {
        void onExecute(T data);
    }
}
