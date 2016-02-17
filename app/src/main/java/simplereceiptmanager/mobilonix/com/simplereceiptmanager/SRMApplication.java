package simplereceiptmanager.mobilonix.com.simplereceiptmanager;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SRMApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = this;

        initFontLibrary();
        initMemoryAnalyzer();
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    private static RefWatcher refWatcher;

    public static Context getInstance() {
        return context;
    }

    public void initFontLibrary() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Raleway-Medium.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public void initMemoryAnalyzer() {
        refWatcher = LeakCanary.install(this);
    }
}
