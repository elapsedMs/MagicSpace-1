package storm.magicspace.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import storm.magicspace.R;

/**
 * Created by xt on 16/6/17.
 */
public class FloatView extends ImageView {


    private final static float SCALE_FACTOR = 1f;
    private final static float SCALE_MIN_FACTOR = 0.7f;
    private final static float SCALE_MAX_FACTOR = 2.2f;

    private Matrix matrix = new Matrix();
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private Bitmap lt_Bitmap;
    private Bitmap rt_Bitmap;
    private Bitmap rb_Bitmap;
    private Bitmap lb_Bitmap;
    private Rect lt_Rect;
    private Rect rt_Rect;
    private Rect rb_Rect;
    private Rect lb_Rect;
    private int lt_Width;
    private int lt_Height;
    private int rt_Width;
    private int rt_Height;
    private int rb_Height;
    private int lb_Width;
    private int lb_Height;
    private int rb_Width;
    private FloatListener mListener;
    private boolean mTriggerScaleAction = false;
    private boolean mTriggerMoveAction;
    private float mRotateDegree;
    private PointF mMiddlePoint = new PointF();
    private float mToCenterDistance;
    private double mBitmapDiagonalLen;
    private float mLastX;
    private float mLastY;

    public FloatView(Context context) {
        super(context);
        init();
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnFloatListener(FloatListener listener) {
        this.mListener = listener;
    }

    @Override
    public void setImageResource(int resId) {
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = bm;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        mBitmapDiagonalLen =  Math.hypot(bitmapWidth, bitmapHeight);
        matrix.postTranslate((mWidth - bitmapWidth) / 2, (mHeight - bitmapHeight) / 2);
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2.0f);

        lt_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.game_egg_opacity);
        rt_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.game_egg_rotation);
        rb_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.game_egg_scaling);
        lb_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        lt_Width = (int) (lt_Bitmap.getWidth() * SCALE_FACTOR);
        lt_Height = (int) (lt_Bitmap.getHeight() * SCALE_FACTOR);
        rt_Width = (int) (rt_Bitmap.getWidth() * SCALE_FACTOR);
        rt_Height = (int) (rt_Bitmap.getHeight() * SCALE_FACTOR);
        rb_Width = (int) (rb_Bitmap.getWidth() * SCALE_FACTOR);
        rb_Height = (int) (rb_Bitmap.getHeight() * SCALE_FACTOR);
        lb_Width = (int) (lb_Bitmap.getWidth() * SCALE_FACTOR);
        lb_Height = (int) (lb_Bitmap.getHeight() * SCALE_FACTOR);

        lt_Rect = new Rect();
        rt_Rect = new Rect();
        rb_Rect = new Rect();
        lb_Rect = new Rect();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        boolean result = true;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX(0);
                float downY = event.getY(0);
                if (isInside(downX, downY, lt_Rect)) {// transparent
                    doHyalinize();
                } else if (isInside(downX, downY, rt_Rect)) {// rotate
                    doRotate();
                } else if (isInside(downX, downY, rb_Rect)) {// scale
                    doScale(event);
                } else if (isInBitmap(event)) {
                    doMove(event);
                } else {
                    result = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTriggerScaleAction) {
                    executeScale(event);
                } else if (mTriggerMoveAction) {
                    executeMove(event);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                reset();
                break;
        }

        return result;
    }

    private void reset() {
        mTriggerScaleAction = false;
        mTriggerMoveAction = false;
    }

    private void executeMove(MotionEvent event) {
        float moveX = event.getX(0);
        float moveY = event.getY(0);
        // may be should limit the boundary
        matrix.postTranslate(moveX - mLastX, moveY - mLastY);
        mLastX = event.getX(0);
        mLastY = event.getY(0);
        invalidate();
    }

    private void executeScale(MotionEvent event) {
        // rotate
        matrix.postRotate((rotationToStartPoint(event) - mRotateDegree) * 2, mMiddlePoint.x,
                mMiddlePoint.y);
        mRotateDegree = rotationToStartPoint(event);
        // scale
        float scale = getDiagonalLen(event) / mToCenterDistance;

        float scaleDiagonalLen = getDiagonalLen(event);
        if (((scaleDiagonalLen / (mBitmapDiagonalLen / 2) <= SCALE_MIN_FACTOR)) && scale < 1 ||
                (scaleDiagonalLen / (mBitmapDiagonalLen / 2) >= SCALE_MAX_FACTOR) && scale > 1) {
            scale = 1;
            float moveX = event.getX(0);
            float moveY = event.getY(0);
            if (!isInside(moveX, moveY, rb_Rect)) {
                mTriggerScaleAction = false;
            }
        } else {
            mToCenterDistance = getDiagonalLen(event);
        }
        matrix.postScale(scale, scale, mMiddlePoint.x, mMiddlePoint.y);
        invalidate();
    }

    private void doMove(MotionEvent event) {
        mTriggerMoveAction = true;
        mLastX = event.getX(0);
        mLastY = event.getY(0);
    }

    private void doScale(MotionEvent event) {
        mTriggerScaleAction = true;
        mRotateDegree = rotationToStartPoint(event);
        midPointToStartPoint(event);
        mToCenterDistance = getDiagonalLen(event);
        if (mListener != null) {
            mListener.clickRightBottom();
        }
    }

    private void doRotate() {
        PointF localPointF = new PointF();
        getMidPoint(localPointF);
        matrix.preRotate(-90, localPointF.x, localPointF.y);
        invalidate();
        if (mListener != null) {
            mListener.clickRightTop();
        }
    }

    private void doHyalinize() {
        if (mListener != null) {
            mListener.clickLeftTop();
        }
    }

    private void getMidPoint(PointF pointF) {
        float[] values = new float[9];
        matrix.getValues(values);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float val1 = values[2];
        float val2 = values[5];
        float val7 = values[0] * width + values[1] * height + values[2];
        float val8 = values[3] * width + values[4] * height + values[5];
        pointF.set(Math.abs(val1 - val7) / 2, Math.abs(val2 - val8) / 2);
    }

    private float getDiagonalLen(MotionEvent event) {
        return (float) Math.hypot(event.getX(0) - mMiddlePoint.x, event.getY(0) - mMiddlePoint.y);
    }

    private void midPointToStartPoint(MotionEvent event) {
        float[] values = new float[9];
        matrix.getValues(values);
        float f1 = values[2];
        float f2 = values[5];
        mMiddlePoint.set((f1 + event.getX(0)) / 2, (f2 + event.getY(0)) / 2);
    }

    private float rotationToStartPoint(MotionEvent event) {
        float[] values = new float[9];
        matrix.getValues(values);
        float x = values[2];
        float y = values[5];
        double arc = Math.atan2(event.getY(0) - y, event.getX(0) - x);
        return (float) Math.toDegrees(arc);
    }

    private boolean isInside(float x, float y, Rect rect) {
        return x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom;
    }

    private boolean isInBitmap(MotionEvent event) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float[] values = new float[9];
        matrix.getValues(values);
        float val1 = values[2];
        float val2 = values[5];
        float val3 = values[0] * width + values[2];
        float val4 = values[3] * width + values[5];
        float val5 = values[1] * height + values[2];
        float val6 = values[4] * height + values[5];
        float val7 = values[0] * width + values[1] * height + values[2];
        float val8 = values[3] * width + values[4] * height + values[5];
        float[] xVals = {val1, val3, val7, val5};
        float[] yVals = {val2, val4, val8, val6};
        return pointInRect(xVals, yVals, event.getX(0), event.getY(0));
    }

    private boolean pointInRect(float[] xRange, float[] yRange, float x, float y) {
        double a1 = Math.hypot(xRange[0] - xRange[1], yRange[0] - yRange[1]);
        double a2 = Math.hypot(xRange[1] - xRange[2], yRange[1] - yRange[2]);
        double a3 = Math.hypot(xRange[3] - xRange[2], yRange[3] - yRange[2]);
        double a4 = Math.hypot(xRange[0] - xRange[3], yRange[0] - yRange[3]);
        double b1 = Math.hypot(x - xRange[0], y - yRange[0]);
        double b2 = Math.hypot(x - xRange[1], y - yRange[1]);
        double b3 = Math.hypot(x - xRange[2], y - yRange[2]);
        double b4 = Math.hypot(x - xRange[3], y - yRange[3]);
        double u1 = (a1 + b1 + b2) / 2;
        double u2 = (a2 + b2 + b3) / 2;
        double u3 = (a3 + b3 + b4) / 2;
        double u4 = (a4 + b4 + b1) / 2;
        double s = a1 * a2;
        double ss = Math.sqrt(u1 * (u1 - a1) * (u1 - b1) * (u1 - b2))
                + Math.sqrt(u2 * (u2 - a2) * (u2 - b2) * (u2 - b3))
                + Math.sqrt(u3 * (u3 - a3) * (u3 - b3) * (u3 - b4))
                + Math.sqrt(u4 * (u4 - a4) * (u4 - b4) * (u4 - b1));
        return Math.abs(s - ss) < 0.5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            float[] values = new float[9];
            matrix.getValues(values);

            canvas.save();

            canvas.drawBitmap(mBitmap, matrix, mPaint);

            float val1 = values[2];
            float val2 = values[5];
            float val3 = values[0] * width + values[2];
            float val4 = values[3] * width + values[5];
            float val5 = values[1] * height + values[2];
            float val6 = values[4] * height + values[5];
            float val7 = values[0] * width + values[1] * height + values[2];
            float val8 = values[3] * width + values[4] * height + values[5];

            positionRect(lt_Rect, val1, val2, lt_Width, lt_Height);
            positionRect(rt_Rect, val3, val4, rt_Width, rt_Height);
            positionRect(lb_Rect, val5, val6, lb_Width, lb_Height);
            positionRect(rb_Rect, val7, val8, rb_Width, rb_Height);

            drawLines(canvas, val1, val2, val3, val4, val5, val6, val7, val8);

            drawBitmaps(canvas);

            canvas.restore();
        }
    }

    private void positionRect(Rect rect, float val1, float val2, int width, int height) {
        rect.left = (int) (val1 - width / 2);
        rect.right = (int) (val1 + width / 2);
        rect.top = (int) (val2 - height / 2);
        rect.bottom = (int) (val2 + height / 2);
    }

    private void drawBitmaps(Canvas canvas) {
        canvas.drawBitmap(lt_Bitmap, null, lt_Rect, mPaint);
        canvas.drawBitmap(rt_Bitmap, null, rt_Rect, mPaint);
        canvas.drawBitmap(rb_Bitmap, null, rb_Rect, mPaint);
        //canvas.drawBitmap(lb_Bitmap, null, lb_Rect, mPaint);
    }

    private void drawLines(Canvas canvas, float val1, float val2, float val3, float val4,
                           float val5, float val6, float val7, float val8) {
        canvas.drawLine(val1, val2, val3, val4, mPaint);
        canvas.drawLine(val3, val4, val7, val8, mPaint);
        canvas.drawLine(val7, val8, val5, val6, mPaint);
        canvas.drawLine(val5, val6, val1, val2, mPaint);
    }

    public interface FloatListener {
        void clickLeftTop();

        void clickRightTop();

        void clickRightBottom();
    }
}
