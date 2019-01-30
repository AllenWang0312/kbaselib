package edu.tjrac.swant.kotlin.baselib.uncom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by wpc on 2018/1/15 0015.
 */

public class CameraUtil {

    final static int TAKE_PHOTO =1 ;

    public static void takePhotoWithSystemApp(Activity context,Uri uri) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(uri!=null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        context.startActivityForResult(intent, TAKE_PHOTO);
    }
}
