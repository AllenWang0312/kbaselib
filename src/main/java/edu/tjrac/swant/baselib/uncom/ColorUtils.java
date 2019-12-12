package edu.tjrac.swant.baselib.uncom;


import android.graphics.Color;
import android.util.Log;

/**
 * Created by wpc on 2016/9/18.
 */
public class ColorUtils {

    public static float getProgress(float from, float to, float current) {
        return (current - from) / (to - from);
    }

    public static int getPrecentColor(int fromcolor, int tocolor, float precent) {
//        int pa = Color.alpha(color);
        int fr = Color.red(fromcolor);
        int fg = Color.green(fromcolor);
        int fb = Color.blue(fromcolor);

        int tr = Color.red(tocolor);
        int tg = Color.green(tocolor);
        int tb = Color.blue(tocolor);
//        int alpha = (int) (pa + (0xff - pa) * persent);
        int red = (int) (fr + (tr - fr) * precent);
        int green = (int) (fg + (tg - fg) * precent);
        int blue = (int) (fb + (tb - fb) * precent);
        return Color.rgb(red, green, blue);
    }

//    public static int getPrecentColor(int fromcolor, int centercolor, int tocolor, float precent) {
////        int pa = Color.alpha(color);
//        int fr = Color.red(fromcolor);
//        int fg = Color.green(fromcolor);
//        int fb = Color.blue(fromcolor);
//
//        int tr = Color.red(tocolor);
//        int tg = Color.green(tocolor);
//        int tb = Color.blue(tocolor);
////        int alpha = (int) (pa + (0xff - pa) * persent);
//        int red = (int) (fr + (tr - fr) * precent);
//        int green = (int) (fg + (tg - fg) * precent);
//        int blue = (int) (fb + (tb - fb) * precent);
//        return Color.rgb(red, green, blue);
//    }

    public static int getLightColor(int color, float persent) {
//        int pa = Color.alpha(color);
        int pr = Color.red(color);
        int pg = Color.green(color);
        int pb = Color.blue(color);
//        int alpha = (int) (pa + (0xff - pa) * persent);
        int red = (int) (pr + (0xff - pr) * persent);
        int green = (int) (pg + (0xff - pg) * persent);
        int blue = (int) (pb + (0xff - pb) * persent);
        return Color.rgb(red, green, blue);
    }

    public static int getDarkColor(int color, float persent) {
//        int pa = Color.alpha(color);
        int pr = Color.red(color);
        int pg = Color.green(color);
        int pb = Color.blue(color);

//        int alpha = (int) (pa * (1 - persent));
        int red = (int) (pr * (1 - persent));
        int green = (int) (pg * (1 - persent));
        int blue = (int) (pb * (1 - persent));
        return Color.rgb(red, green, blue);
    }


    public static int getFeatureColor(char[] str) {
        int c1 = (int) str[1];
        int c2 = (int) str[2];
        int c3 = (int) (256 - c1 - c2);
        int color = c1 << 8 + c2 << 4 + c3;
        Log.i("getFeatureColor", color + "");
        return color;
    }
}
