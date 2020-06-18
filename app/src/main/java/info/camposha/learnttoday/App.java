package info.camposha.learnttoday;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * This is our application class. It will extend the android.app.Application
 * class. We do any initializations that we want here. For example initializing
 * our custom fonts.
 *
 * Don't forget to register this class in the android manifest:
 *     <application
 *         android:name=".App"
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/chalkboard.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
}
//end
