package storm.commonlib.common.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import storm.commonlib.R;
import storm.commonlib.common.BaseApplication;
import storm.commonlib.common.http.baseHttpBean.BaseResponse;
import storm.commonlib.common.util.MessageUtil;

import static storm.commonlib.common.util.StringUtil.EMPTY;

public class BaseASyncTask<T, V extends BaseResponse> extends AsyncTask<T, Void, V> {

    private ProgressDialog medtreeDialog = null;

    public BaseASyncTask() {
    }

    public BaseASyncTask(Activity context) {
        showLoadingDialog(context);
    }

    public BaseASyncTask(Context context, boolean hasLoadingDialog) {
        if (hasLoadingDialog) showLoadingDialog(context);
    }

    @SafeVarargs
    @Override
    public final V doInBackground(T... params) {
        return doRequest(params.length == 0 ? null : params[0]);
    }

    @Override
    protected void onPostExecute(V v) {
        super.onPostExecute(v);
        disMissLoadingDialog();
        if (v == null) {
            onFailed();
            return;
        }

        if (v.status != 0 && v.status >= 1000) {
            MessageUtil.showMessage(v.status_msg == null || v.status_msg.equals(EMPTY) ? BaseApplication.getApplication().getString(R.string.error_network) : v.status_msg);
            onFailed();
        } else {
            if (v.getData() != null) {
                onSuccess(v);
            } else {
                onSuccessWithoutResult(v);
            }
        }
    }

    public V doRequest(T param) {
        return null;
    }

    public void onFailed() {
    }

    public void onSuccess(V v) {
    }

    public void onSuccessWithoutResult(V v) {

    }

    private void showLoadingDialog(Context context) {
        if (context == null) return;
        medtreeDialog = medtreeDialog == null ? new ProgressDialog(context) : medtreeDialog;
        medtreeDialog.setMessage(context.getString(R.string.data_loading));
        medtreeDialog.setCancelable(true);

        if (!medtreeDialog.isShowing())
            medtreeDialog.show();
    }

    private void disMissLoadingDialog() {
        if (medtreeDialog != null)
            medtreeDialog.dismiss();
    }

}
