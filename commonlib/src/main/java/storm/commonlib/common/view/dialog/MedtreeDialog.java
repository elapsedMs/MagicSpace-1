package storm.commonlib.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import storm.commonlib.R;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MedtreeDialog extends Dialog {
    private TextView titleView;
    private TextView messageView;
    private Button positiveButton;
    private Button negativeButton;
    private ImageView waitingSpinner;
    private AnimationDrawable animationDrawable;
    private View splitView;
    private boolean isAnimation;
    private static final int DEFAULT_ANIMATION_RESOURCE_ID = R.anim.query_loading_animation;
    private LinearLayout button_view;
    private View div_line;
    private LinearLayout ll_whole_view;
    private RelativeLayout rl_whole_view;
    private RelativeLayout dialog_total_view;

    public static class MedTreeDialogBuilder {
        private MedtreeDialog medtreeDialog;

        public MedTreeDialogBuilder builderDialog(Context context) {
            medtreeDialog = new MedtreeDialog(context);
            return this;
        }

        public MedTreeDialogBuilder builderTitle(CharSequence title) {
            this.medtreeDialog.setTitle(title);
            return this;
        }

        public MedTreeDialogBuilder withoutAnimation() {
            return this;
        }

        public MedTreeDialogBuilder buildMessage(CharSequence message) {
            this.medtreeDialog.setMessage(message);
            return this;
        }

        public void show() {
            this.medtreeDialog.show();
        }
    }

    public MedtreeDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        initUi();

        initListener();
    }

    @Override
    public void show() {
        if (this.isShowing()) return;
        super.show();

        if (this.isAnimation)
            startAnimation();
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initUi() {
        setContentView(R.layout.view_confirm_dialog);

        titleView = (TextView) findViewById(R.id.title);
        messageView = (TextView) findViewById(R.id.message);
        waitingSpinner = (ImageView) findViewById(R.id.waiting_spinner);

        positiveButton = (Button) findViewById(R.id.positive_button);
        splitView = findViewById(R.id.split_view);
        negativeButton = (Button) findViewById(R.id.negative_button);
        button_view = (LinearLayout) findViewById(R.id.button_view);
        div_line = findViewById(R.id.div_line);
        ll_whole_view = (LinearLayout) findViewById(R.id.ll_whole_view);
        rl_whole_view = (RelativeLayout) findViewById(R.id.rl_whole_view);
        dialog_total_view = (RelativeLayout) findViewById(R.id.dialog_total_view);
    }

    @Override
    public void setTitle(CharSequence title) {
        titleView.setText(title);
        titleView.setVisibility(VISIBLE);
    }

    public void setPositiveButton(String positiveButtonText, View.OnClickListener positiveListener) {
        positiveButton.setText(positiveButtonText);
        positiveButton.setOnClickListener(positiveListener);
    }

    public void setNegativeButton(String negativeButtonText, View.OnClickListener negativeListener) {
        negativeButton.setText(negativeButtonText);
        negativeButton.setOnClickListener(negativeListener);
    }

    public void displayWithStyle(DisplayStyle style) {
        negativeButton.setVisibility(style.negativeVisibility);
        positiveButton.setVisibility(style.positiveVisibility);

        splitView.setVisibility(style == DisplayStyle.POSITIVE_AND_NEGATIVE ? VISIBLE : GONE);

        switch (style) {
            case LOADING:
                startAnimation();
                setTitle(getContext().getString(R.string.data_loading));
                messageView.setVisibility(GONE);
                this.setCancelable(false);
                break;
        }
    }

    public void startAnimation() {
        waitingSpinner.setVisibility(VISIBLE);
        waitingSpinner.setBackgroundResource(R.anim.query_loading_animation);

        animationDrawable = (AnimationDrawable) waitingSpinner.getBackground();
        animationDrawable.start();

        messageView.setVisibility(INVISIBLE);
    }

    public void withCustomAnimation(int animationResourceId) {
        this.isAnimation = true;

        waitingSpinner.setVisibility(VISIBLE);
        waitingSpinner.setBackgroundResource(animationResourceId);
    }

    public void withDefaultAnimation() {
        withCustomAnimation(DEFAULT_ANIMATION_RESOURCE_ID);
    }

    public void setMessage(CharSequence message) {
        messageView.setText(message);
        messageView.setVisibility(VISIBLE);

        stopAnimation();
    }

    private void stopAnimation() {
        if (animationDrawable != null) {
            animationDrawable.stop();
            waitingSpinner.setVisibility(GONE);
        }
    }

    public void hideBottom() {
        button_view.setVisibility(View.GONE);
        positiveButton.setVisibility(View.GONE);
        negativeButton.setVisibility(View.GONE);
        splitView.setVisibility(View.GONE);
        div_line.setVisibility(View.GONE);
    }

    public void hideNegativeButton() {
        negativeButton.setVisibility(GONE);
        splitView.setVisibility(GONE);
    }

    public enum DisplayStyle {
        LOADING(GONE, GONE),
        POSITIVE(VISIBLE, GONE),
        NEGATIVE(GONE, VISIBLE),
        POSITIVE_AND_NEGATIVE(VISIBLE, VISIBLE);

        public int positiveVisibility = VISIBLE;
        public int negativeVisibility = VISIBLE;

        DisplayStyle(int positiveVisibility, int negativeVisibility) {
            this.positiveVisibility = positiveVisibility;
            this.negativeVisibility = negativeVisibility;
        }
    }

    public void setTextSize(float i) {
        messageView.setTextSize(i);
    }

    public void setTextColor(int i) {
        messageView.setTextColor(i);
    }

}