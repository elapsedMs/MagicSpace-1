package storm.magicspace.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import storm.magicspace.R;

/**
 * Created by xt on 16/6/17.
 */
public class FloatView extends ImageView {


    private Paint mPaint;
    private int mScreenWidth;
    private int mScreenHeight;
    private Bitmap mBitmap;
    private Matrix matrix = new Matrix();
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
    private float SCALE_FACTOR = 1f;


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

    public void load(Bitmap bitmap) {
        mBitmap = bitmap;
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
        mScreenWidth  = w;
        mScreenHeight = h;
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        matrix.postTranslate(mScreenWidth / 2 - bitmapWidth / 2,
                mScreenHeight / 2 - bitmapHeight / 2);
    }

    private void init() {

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        mScreenWidth = displayMetrics.widthPixels;
//        mScreenHeight = displayMetrics.heightPixels;

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

        //setImageResource(R.mipmap.surprise_egg_red);

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
        canvas.drawLine(val5, val6, val7, val8, mPaint);
        canvas.drawLine(val5, val6, val1, val2, mPaint);
    }

    private void positionRect(Rect rect, float val1, float val2, int width, int height) {
        rect.left = (int) (val1 - width / 2);
        rect.right = (int) (val1 + width / 2);
        rect.top = (int) (val2 - height / 2);
        rect.bottom = (int) (val2 + height / 2);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        //matrix.postScale(initScale, initScale, w / 2, h / 2);
        matrix.postTranslate(mScreenWidth / 2 - w / 2, mScreenHeight / 2 - h / 2);
        invalidate();

    }
}
