package edu.tjrac.swant.baselib.util

import android.app.Activity
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import java.io.File

/**
 * 类描述:
 * 创建人: Administrator
 * 创建时间: 2018/3/31 0031 下午 1:55
 * 修改人:
 * 修改时间:
 * 修改备注:
 */

class IntentUtil {

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun ignoreBatteryOptimization(activity: Activity) {
        val powerManager = activity.getSystemService(POWER_SERVICE) as PowerManager
        val hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.packageName)
        //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
        if (!hasIgnored) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:" + activity.packageName)
            activity.startActivity(intent)
        }
    }


    internal var PHOTO_REQUEST_GALLERY = 1

    /*
    * 从相册获取
     */
    fun gallery(context: Activity) {
        // 激活系统图库，选择一张图片
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        context.startActivityForResult(intent, PHOTO_REQUEST_GALLERY)
    }

    companion object {
        /**
         * 打开文件
         *
         * @param file
         */
        fun openFileIntent( file: File): Intent {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //设置intent的Action属性
            intent.action = Intent.ACTION_VIEW
            //获取文件file的MIME类型
            val type = FileUtils.getMIMEType(file)
            //设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(file), type)
            return intent
        }

        fun getCallPhoneIntent(tel: String): Intent {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:" + tel)
            intent.data = data
            return intent
        }

        fun getCallPrivilegedIntent(tel: String): Intent {

            val intent = Intent("android.intent.action.CALL_PRIVILEGED")
            val data = Uri.parse("tel:" + tel)
            intent.data = data
            return intent
        }

        fun openUrlWithSystemChrome(url: String): Intent {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(url)
            return intent
        }

        //    ACTION_IMAGE_CAPTURE 拍摄一张
        //ACTION_IMAGE_CAPTURE_SECURE 吊起相机
        //INTENT_ACTION_STILL_IMAGE_CAMERA
        fun TakePhotoIntent(abspath: String): Intent {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri = Uri.fromFile(File(abspath))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            return intent
        }

        fun toMarket(context: Context, appPkg: String, marketPkg: String?): Intent {
            val uri = Uri.parse("market://details?id=" + appPkg)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (marketPkg != null) {// 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
                intent.`package` = marketPkg
            }
            return intent

        }
    }
}
