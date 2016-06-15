package storm.commonlib.common.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import storm.commonlib.R;


/**
 * Created by gdq on 16/1/14.
 */
public class ContentDialog extends BaseDialog {

    public static final int STYLE_NORMAL = 1;
    public static final int STYLE_TITLE = 2;
    public static final int STYLE_NOTICE = 3;
    public static final int STYLE_TITL_AND_NOTICE = 4;

    private TextView content;
    private TextView leftTv;
    private TextView rightTv;
    private LinearLayout cancelLl;
    private LinearLayout confirmLl;
    private TextView titleTv;
    private LinearLayout cancelAndConfirmLl;
    private LinearLayout knowLl;
    private View titleLine;
    private TextView knowTv;

    public ContentDialog(Context context, int theme, int layoutId) {
        super(context, theme, layoutId);
    }

    @Override
    public void init(Context context, int layoutId) {
        super.init(context, layoutId);
        getWindow().setGravity(Gravity.CENTER_VERTICAL);
        leftTv = (TextView) this.layout.findViewById(R.id.tv_left_content);
        rightTv = (TextView) this.layout.findViewById(R.id.tv_right_content);
        cancelLl = (LinearLayout) this.layout.findViewById(R.id.ll_cancel);
        content = (TextView) this.layout.findViewById(R.id.tv_content);
        confirmLl = (LinearLayout) this.layout.findViewById(R.id.ll_confirm);
        titleTv = (TextView) this.layout.findViewById(R.id.dialog_title);
        cancelAndConfirmLl = (LinearLayout) this.layout.findViewById(R.id.ll_cancel_and_confirm);
        knowLl = (LinearLayout) this.layout.findViewById(R.id.ll_know);
        titleLine = this.layout.findViewById(R.id.title_line);
        knowTv = (TextView) this.layout.findViewById(R.id.tv_know);
    }

    public ContentDialog setContent(String title, String content, String leftContent, String rightContent, String know, OnClickListener confirmClickListener, int style) {
        this.titleTv.setText(title);
        this.content.setText(content);
        this.leftTv.setText(leftContent);
        this.rightTv.setText(rightContent);
        this.confirmClickListener = confirmClickListener;
        this.knowTv.setText(know);
        initListener();
        switchStyle(style);
        return this;
    }

    public ContentDialog setContent(String content, String leftContent, String rightContent, OnClickListener confirmClickListener, OnClickListener cancelClickListener, int style) {
        this.content.setText(content);
        this.leftTv.setText(leftContent);
        this.rightTv.setText(rightContent);
        this.confirmClickListener = confirmClickListener;
        this.cancelClickListener = cancelClickListener;
        initListener();
        switchStyle(style);
        return this;
    }

    public void setContentNullMargin() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) content.getLayoutParams();
        params.setMargins(0, 40, 0, 40);
        content.setLayoutParams(params);
        content.setTextSize(18);
        leftTv.setTextSize(18);
        rightTv.setTextSize(18);
    }

    private void switchStyle(int style) {
        switch (style) {
            case STYLE_NORMAL:
                titleLine.setVisibility(View.GONE);
                titleTv.setVisibility(View.GONE);
                knowLl.setVisibility(View.GONE);
                cancelAndConfirmLl.setVisibility(View.VISIBLE);
                break;
            case STYLE_TITLE:
                titleLine.setVisibility(View.VISIBLE);
                titleTv.setVisibility(View.VISIBLE);
                knowLl.setVisibility(View.GONE);
                cancelAndConfirmLl.setVisibility(View.VISIBLE);
                break;
            case STYLE_NOTICE:
                titleLine.setVisibility(View.GONE);
                titleTv.setVisibility(View.GONE);
                knowLl.setVisibility(View.VISIBLE);
                cancelAndConfirmLl.setVisibility(View.GONE);
                break;
            case STYLE_TITL_AND_NOTICE:
                titleLine.setVisibility(View.VISIBLE);
                titleTv.setVisibility(View.VISIBLE);
                knowLl.setVisibility(View.VISIBLE);
                cancelAndConfirmLl.setVisibility(View.GONE);
                break;
        }
    }

    private void initListener() {
        cancelLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelClickListener == null) {
                    dismiss();
                    return;
                }
                cancelClickListener.onClick(ContentDialog.this, 0);
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClickListener.onClick(ContentDialog.this, 0);
            }
        });
        knowLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder extends BaseDialog.Builder<ContentDialog> {
        public Builder(Context context, int dialogStyle) {
            e = new ContentDialog(context, dialogStyle, R.layout.dialog_content);
        }

        public Builder content(String content, String leftContent, String rightContent, OnClickListener confirmClickListener) {
            e.setContent("", content, leftContent, rightContent, "", confirmClickListener, STYLE_NORMAL);
            return this;
        }

        public Builder content(String title, String content, String leftContent, String rightContent, String know, OnClickListener confirmClickListener, int style) {
            e.setContent(title, content, leftContent, rightContent, know, confirmClickListener, style);
            return this;
        }

        public Builder content(String content, String leftContent, String rightContent, OnClickListener confirmClickListener, OnClickListener cancelClickListener) {
            e.setContentNullMargin();
            e.setContent(content, leftContent, rightContent, confirmClickListener, cancelClickListener, STYLE_NORMAL);
            return this;
        }
    }
}
