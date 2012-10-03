package cacafogo.software.stealth;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

public class ApplicationStuff extends Application {
    private static ApplicationStuff me;

    public ApplicationStuff() {
        me = this;
    }

    public static Context Context() {
        return me;
    }

    public static ContentResolver ContentResolver() {
        return me.getContentResolver();
    }
}
