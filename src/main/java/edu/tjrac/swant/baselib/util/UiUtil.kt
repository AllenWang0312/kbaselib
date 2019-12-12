package edu.tjrac.swant.baselib.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import edu.tjrac.swant.baselib.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Created by wpc on 2018-08-02.
 */

object UiUtil {
    fun saveViewToImage(view: View, path: String): Boolean {
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.drawingCacheBackgroundColor = Color.WHITE
        var cache = loadBitmapFromView(view)
        return saveBitmapToFile(cache, path)
    }

    private fun saveBitmapToFile(cache: Bitmap, path: String): Boolean {
        var fos: FileOutputStream? = null
        try {
            var file = File(path)
            if (!file.exists()) {
                file.createNewFile()
            }
            fos = FileOutputStream(file)
            cache.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
            return true
        } catch (e: java.lang.Exception) {
            return false
        }
    }

    @SuppressLint("NewApi", "RestrictedApi")
    fun showPopmenu(
            context: Context, v: View,
            withIcon: Boolean,
            menuId: Int, onMenuItemClickListener: PopupMenu.OnMenuItemClickListener
    ): PopupMenu {
        val popup = PopupMenu(context, v)
        if (withIcon) {
            try {
                val field = popup.javaClass.getDeclaredField("mPopup")
                field.isAccessible = true
                val mHelper = field.get(popup) as MenuPopupHelper
                mHelper.setForceShowIcon(true)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

        }
        val inflater = popup.menuInflater
        inflater.inflate(menuId, popup.menu)
        popup.setOnMenuItemClickListener(onMenuItemClickListener)
        popup.show()
        return popup
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        var w = v.width
        var h = v.height
        var bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        var c = Canvas(bmp)
        c.drawColor(Color.WHITE)
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    fun createEmpty(context: Context, resId: Int, hint: String): View {
        var empty = LayoutInflater.from(context).inflate(R.layout.empty, null)
        empty.findViewById<ImageView>(R.id.iv_empty).setImageResource(resId)
        if (!SUtil.isEmpty(hint)) {
            empty.findViewById<TextView>(R.id.tv_empty).text = hint
        }
        return empty
    }

    fun dp2px(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                context.resources.displayMetrics)
    }

    fun sp2px(context: Context, sp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(),
                context.resources.displayMetrics)
    }

    fun getNavigationHeight(context: Context): Int {
        if (isHaveNavigationBar(context)) {
            return context.resources.getIdentifier("navigation_bar_height", "dimen", "android");
        } else {
            return 0
        }
    }

    fun isHaveNavigationBar(context: Context): Boolean {

        var isHave = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            isHave = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                isHave = false
            } else if ("0" == navBarOverride) {
                isHave = true
            }
        } catch (e: Exception) {
            Log.w("TAG", e)
        }
        return isHave
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val height = resources.getDimensionPixelSize(resourceId)
        return height
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return wm.defaultDisplay.width
    }

    fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorResId)

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     *
     */
    fun setStatusBar(activity: Activity, translate: Boolean, lightStatusTextColor: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上

            if (!translate) {
                activity.window.statusBarColor = activity.resources
                        .getColor(R.color.colorPrimaryDark)
            } else {
                val decorView = activity.window.decorView
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = activity.window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
        setStatusTextColor(lightStatusTextColor, activity)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
    }

    /**
     * 设置状态栏文字色值为深色调

     * @param useDart  是否使用深色调
     * *
     * @param activity
     */
    fun setStatusTextColor(useDart: Boolean, activity: Activity) {

        if (isFlyme) {
//            return
            processFlyMe(useDart, activity)
        } else if (isMIUI) {
            processMIUI(useDart, activity)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (useDart) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
                activity.getWindow().getDecorView().findViewById<View>(android.R.id.content).setPadding(0, 0, 0,
                        0
                );
            }
        }
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private fun processMIUI(lightStatusBar: Boolean, activity: Activity) {
        val clazz = activity.window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (lightStatusBar) darkModeFlag else 0, darkModeFlag)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private fun processFlyMe(isLightStatusBar: Boolean, activity: Activity) {
        val lp = activity.window.attributes
        try {
            val instance = Class.forName("android.view.WindowManager\$LayoutParams")
            val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
            val field = instance.getDeclaredField("meizuFlags")
            field.isAccessible = true
            val origin = field.getInt(lp)
            if (isLightStatusBar) {
                field.set(lp, origin or value)
            } else {
                field.set(lp, value.inv() and origin)
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

    /**
     * 判断手机是否是小米

     * @return
     */
    val isMIUI: Boolean
        get() {
            try {
                val prop = BuildProperties.newInstance()
                return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                        || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                        || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null
            } catch (e: IOException) {
                return false
            }

        }

    /**
     * 判断手机是否是魅族

     * @return
     */
    // Invoke Build.hasSmartBar()
    val isFlyme: Boolean
        get() {
            try {
                val method = Build::class.java.getMethod("hasSmartBar")
                return method != null
            } catch (e: Exception) {
                return false
            }

        }

    private class BuildProperties @Throws(IOException::class)
    private constructor() {

        private val properties: Properties

        init {
            properties = Properties()
            properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
        }

        fun getProperty(name: String, defaultValue: String?): String? {
            return properties.getProperty(name, defaultValue)
        }

        companion object {

            @Throws(IOException::class)
            fun newInstance(): BuildProperties {
                return BuildProperties()
            }
        }
    }


    @SuppressLint("WrongConstant")
    fun visiableORgone(view: View, visiable: Boolean) {
        view.visibility = if (visiable) View.VISIBLE else View.GONE
    }

    fun inflateGallery(context: Context, ll: LinearLayout?, padding: Int, images: ArrayList<String>) {
        var width = ll?.width!!
        var itemWidth = (width - padding * 4) / 3

        var frame = FrameLayout(context)
        frame.layoutParams = LinearLayout.LayoutParams(width, width)
        for (i in 0 until images.size) {
            var url = images.get(i)
            var image = ImageView(context)
            image.layoutParams = FrameLayout.LayoutParams(itemWidth, itemWidth)
            Glide.with(context).load(url).into(image)
            frame.addView(image, (i % 3) * itemWidth + (i % 3 + 1) * padding, (i / 3) * itemWidth + (i / 3 + 1) * padding)
        }
        ll.addView(frame)
    }

    fun disableShiftMode(navigationView: BottomNavigationView) {
        val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false

            for (i in 0 until menuView.childCount) {
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
//                itemView.setShiftingMode(false)
                itemView.setChecked(itemView.itemData.isChecked)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
