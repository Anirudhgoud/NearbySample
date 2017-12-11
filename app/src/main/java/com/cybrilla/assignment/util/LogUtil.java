package com.cybrilla.assignment.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Anirudh on 10-12-2017.
 */

public class LogUtil {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
