package storm.magicspace.view.handmark.pulltorefresh.library.internal;


import storm.commonlib.common.util.LogUtil;

public class Utils {

    static final String LOG_TAG = "PullToRefresh";

    public static void warnDeprecation(String depreacted, String replacement) {
        LogUtil.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }

}
