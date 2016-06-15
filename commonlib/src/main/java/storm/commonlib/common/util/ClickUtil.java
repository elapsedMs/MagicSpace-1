package storm.commonlib.common.util;

public class ClickUtil {

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClick(int disTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < disTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}