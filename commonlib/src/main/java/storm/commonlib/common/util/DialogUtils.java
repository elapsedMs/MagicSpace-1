//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
package storm.commonlib.common.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import storm.commonlib.R;
import storm.commonlib.common.Constants;
import storm.commonlib.common.view.dialog.ContentDialog;
import storm.commonlib.common.view.dialog.ListDialog;


/**
 * Created by gdq on 16/1/15.
 */
public class DialogUtils {

    private static ListDialog listDialog;
    private static ContentDialog contentDialog;

    public static void dimissPortraitDialog() {
        if (listDialog != null && listDialog.isShowing()) {
            listDialog.dismiss();
            listDialog = null;
        }
    }

    public static void showContentDialog(Activity context, String content, String cancelString, String confirmString, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener) {
        if (contentDialog != null && contentDialog.isShowing()) return;
        contentDialog = new ContentDialog.Builder(context, R.style.DialogStyle).content(content, cancelString, confirmString, listener, cancelListener).build();
        contentDialog.show();
    }

    public static void showContentDialog(Activity context, String content, String cancelString, String confirmString, DialogInterface.OnClickListener listener) {
        if (contentDialog != null && contentDialog.isShowing()) return;
        contentDialog = new ContentDialog.Builder(context, R.style.DialogStyle).content(content, cancelString, confirmString, listener).build();
        contentDialog.show();
    }

    public static void showContentDialog(Activity context, String title, String content, String cancelString, String confirmString, String know, int style, DialogInterface.OnClickListener listener) {
        if (contentDialog != null && contentDialog.isShowing()) return;
        contentDialog = new ContentDialog.Builder(context, R.style.DialogStyle).content(title, content, cancelString, confirmString, know, listener, style).build();
        contentDialog.show();
    }

    public static void dismissContentDialog() {
        if (contentDialog != null && contentDialog.isShowing()) {
            contentDialog.dismiss();
            contentDialog = null;
        }
    }

    public static void showCallDialog(final Activity context, String content) {
        DialogUtils.showContentDialog(context, content, "否", "是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.LITTLE_HELPER_PHONE_NUMBER));
                context.startActivity(intent);
                DialogUtils.dismissContentDialog();
            }
        });
    }

}
