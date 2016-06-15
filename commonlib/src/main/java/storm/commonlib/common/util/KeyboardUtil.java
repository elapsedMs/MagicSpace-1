package storm.commonlib.common.util;//package com.medtree.client.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.roboguice.shaded.goole.common.base.Predicate;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static java.util.Arrays.asList;
import static org.roboguice.shaded.goole.common.collect.Iterables.any;

public class KeyboardUtil {

    public static void hideKeyBoardWhenNotInView(Context context, final MotionEvent event, View... views) {
        boolean shouldNotHide = any(asList(views), new Predicate<View>() {
            @Override
            public boolean apply(View view) {
                return isTouchInView(event, view);
            }
        });

        List<View> viewList = asList(views);
        if (!shouldNotHide) {
            hideKeyboard(context, viewList.get(0));
        }
    }

    public static void hideKeyBoardWhenNotInView(Context context, MotionEvent event, View view) {
        boolean shouldHide = !isTouchInView(event, view);

        if (shouldHide) {
            hideKeyboard(context, view);
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), HIDE_NOT_ALWAYS);
    }

    private static boolean isTouchInView(MotionEvent event, View view) {
        int[] location = {0, 0};

        view.getId();
        view.getLocationInWindow(location);

        int left = location[0];
        int top = location[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();

        return event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom;
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}