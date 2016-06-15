package storm.commonlib.common.http;


/**
 * 远程同步类
 */
public class RemotingSyncProvider {
    /**
     * 远程数据同步
     * @param urlPath   请求的URL
     */
    public static void remotingSync(String urlPath) {
        // 检查行为日志上报
        if (!urlPath.contains("behavior") && !urlPath.contains("user/login")) {
            BehaviorProvider.uploadBehaviorInfo(false);
        }
        // 上报留给自己
        //if (!urlPath.contains("mood/batch")) {
        //    uploadMoodList();
        //}
    }

    /**
     * 上传留给自己
     */
    /*private static void uploadMoodList() {
        // 是否有需要上传的数据
        List<MoodInfo> list = LocalRepository.getUploadMoodInfoList();
        if (list==null || list.size()<=0) return;

        // 同步处理数据
        boolean result = RemotingAccountService.saveMoodList(list);

        // 更新本地数据状态
        if (result) {
            LocalRepository.updateMoodState(list);
        }
    }*/
}
