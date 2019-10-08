package edu.tjrac.swant.baselib.util

import android.os.Environment

/**
 * Created by wpc on 2019-07-19.
 */
class Phone {
    companion object{
        //    内置sd卡路径
//    String sdcardPath = System.getenv("EXTERNAL_STORAGE");
//    内置sd卡路径
//    String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //外置置sd卡路径
//    String extSdcardPath = System.getenv("SECONDARY_STORAGE");
        var SDCardPath: String? = null
            get() {
                if (field == null) {
                    field = Environment.getExternalStorageDirectory().path
                }
                return field
            }

        var ExtSDCardPath: String? = null
            get() {
                if (field == null) {
                    field = System.getenv("SECONDARY_STORAGE")
                }
                return field
            }

    }

}