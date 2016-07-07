package storm.magicspace.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import storm.magicspace.R;

/**
 * Created by gdq on 16/6/22.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GridItemDecoration(Context context) {
//        mDivider = context.getResources(R.mipmap.btm_bar_egg);
        mDivider = context.getResources().getDrawable(R.drawable.shape_recyecle_decorator);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                mDivider.getIntrinsicHeight());
    }
}
