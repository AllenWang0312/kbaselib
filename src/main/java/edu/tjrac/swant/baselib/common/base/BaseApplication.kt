package edu.tjrac.swant.baselib.common.base

import android.app.Application

/**
 * Created by wpc on 2018-08-02.
 */

abstract class BaseApplication : Application() {

    var activitys: ArrayList<BaseActivity>? = null


    override fun onCreate() {
        super.onCreate()
        instance=this
    }

    fun addActivity(activity: BaseActivity) {
        if (null === activitys) {
            activitys = ArrayList<BaseActivity>()
        }
        activitys!!.add(activity)
    }

    fun removeActivity(activity: BaseActivity) {
        activitys?.remove(activity)
    }

    fun exit() {
        for (item in activitys!!) {
            item?.finish()
        }
        activitys!!.clear()
    }



    companion object {

        public  var instance: BaseApplication? = null
            set
    }

}