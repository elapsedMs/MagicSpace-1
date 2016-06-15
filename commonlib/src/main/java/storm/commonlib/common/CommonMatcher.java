package storm.commonlib.common;

import java.util.HashMap;


public class CommonMatcher {
    public static String getRelationName(int type) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "自己");
        map.put(1, "配偶");
        map.put(2, "父亲");
        map.put(3, "母亲");
        map.put(4, "儿子");
        map.put(5, "女儿");
        map.put(6, "哥哥");
        map.put(7, "姐姐");
        map.put(8, "弟弟");
        map.put(9, "妹妹");
        map.put(10, "爷爷");
        map.put(11, "奶奶");
        map.put(12, "外公");
        map.put(13, "外婆");
        map.put(14, "自己");
        map.put(15, "自己");
        map.put(99, "其他");
        return map.get(type);
    }

    public static String getGender(int type) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "保密");
        map.put(1, "男");
        map.put(2, "女");
        return map.get(type);
    }

}
