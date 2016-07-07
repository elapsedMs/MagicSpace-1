package storm.commonlib.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by gdq on 16/1/14.
 */
public abstract class BaseDialog extends Dialog implements DialogInterface {
    public Context mContext;
    public View layout;
    public OnClickListener cancelClickListener;
    public OnClickListener confirmClickListener;
    public OnCancelListener onCancelListener;


    public BaseDialog(Context context, int layoutId) {
        super(context, -1);
    }

    public BaseDialog(Context context, int theme, int layoutId) {
        super(context, theme);
        init(context, layoutId);
    }

    public void init(Context context, int layoutId) {
        this.mContext = context;
        layout = View.inflate(context, layoutId, null);
        setContentView(layout);
        setCancelable(true);
    }

    public static class Builder<E extends BaseDialog> {
        public E e;

        public E build() {
            return e;
        }
    }
}
