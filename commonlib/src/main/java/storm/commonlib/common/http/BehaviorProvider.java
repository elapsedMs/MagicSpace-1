package storm.commonlib.common.http;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;

import java.util.List;

/**
 * 用户行为统计提供类
 */
public class BehaviorProvider {

    public static final String BEHAVIOR_ACTION_TYPE = "BEHAVIOR_TYPE";
    public static final String BEHAVIOR_ACTION_DATA = "BEHAVIOR_DATA";

    private static Context mContext;

    private static Context getContext() {
        return mContext;
    }

    // 计时器
    // 最后一次上传数据时间
    private static long mLastUploadTime = 0;
    // 上传数据间隔时间
    private static long mUploadInterval = 30 * 1000;

    // 应用激活状态记录
    private static boolean isActive = true;

    public static void onResume() {
        if (!isActive) {
            isActive = true;
            addBehavior(ActionTypes.mt_app_active);
        }
    }

    public static void onPause() {
        if (!isScreenOn()) {
            isActive = false;
            addBehavior(ActionTypes.mt_app_inactive);
        }
    }

    public static void onStop() {
        if (isAppOnBackground()) {
            isActive = false;
            addBehavior(ActionTypes.mt_app_inactive);
        }
    }

    private static ActivityManager mAM = null;
    private static String mPackageName = null;

    /**
     * 判断当前应用程序处于后台
     */
    private static boolean isAppOnBackground() {
        if (getContext() == null)
            return false;
        if (mAM == null) {
            mAM = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        }
        if (mPackageName == null) {
            mPackageName = getContext().getPackageName();
        }
        List<ActivityManager.RunningTaskInfo> tasks = mAM.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mPackageName)) {
                return true;
            }
        }
        return false;
    }

    private static PowerManager mPM = null;

    /**
     * 判断屏幕是否点亮
     *
     * @return 是否未关闭屏幕
     */
    private static boolean isScreenOn() {
        if (getContext() == null)
            return false;
        if (mPM == null) {
            mPM = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
        }
        return mPM.isScreenOn();
    }

    /**
     * 添加用户行为
     *
     * @param actionType 行为类型
     */
    public static void addBehavior(ActionTypes actionType) {
        addBehavior(actionType, null);
    }

    /**
     * 添加用户行为
     *
     * @param actionType 行为类型
     * @param dataKey    行为数据主键
     */
    public static void addBehavior(ActionTypes actionType, String dataKey) {
        if (actionType == null) return;
        addBehavior(actionType.getValue(), dataKey);
    }

    /**
     * 添加用户行为
     *
     * @param actionType 行为类型
     * @param dataKey    行为数据主键
     */
    public static void addBehavior(int actionType, String dataKey) {
        // TODO white 暂时注释，需要写本地数据库

//        long start_time = System.currentTimeMillis();
//        if (BaseUtils.isEmpty(dataKey)) dataKey = "";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(actionType).append(",").append(start_time).append(",0,").append(dataKey);
//
//        // 存入库表
//        LocalRepository.saveBehaviorInfo(actionType, start_time, 0, sb.toString());
    }

    /**
     * 上传的行为日志信息
     *
     * @param isForce 是否强制执行
     */
    public static void uploadBehaviorInfo(boolean isForce) {
        // TODO white 暂时注释，需要写本地数据库
//        if (!isForce) {
//            // 判断计时器是否到期
//            if (!checkUploadTime()) return;
//
//            // 是否有需要上传的数据
//            if (!LocalRepository.hasBehaviorInfo()) return;
//        }
//
//        // 开始上传数据
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<BehaviorDao> daoList = LocalRepository.getBehaviorList();
//
//                boolean result = false;
//                if (daoList != null && daoList.size() > 0) {
//                    List<String> list = new ArrayList<String>();
//                    for (BehaviorDao dao : daoList) {
//                        list.add(dao.getData());
//                    }
//                    result = RemotingSystemService.uploadBehavior(list);
//                    if (result) {
//                        // 处理成功，清理本地数据库
//                        LocalRepository.deleteBehaviorList(daoList);
//                    }
//                }
//            }
//        }).start();
    }

    /**
     * 检查更新时间
     *
     * @return 是否更新
     */
    private static boolean checkUploadTime() {
        long time = System.currentTimeMillis();
        if (mLastUploadTime <= 0) {
            mLastUploadTime = time;
            return false;
        }

        if (time - mLastUploadTime < mUploadInterval) return false;

        mLastUploadTime = time;
        return true;
    }
}
