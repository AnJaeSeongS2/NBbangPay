package com.npay.hackathon.npay.Widget;

import android.content.Context;

/**
 * Created by Administrator on 2016-09-08.
 */

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
