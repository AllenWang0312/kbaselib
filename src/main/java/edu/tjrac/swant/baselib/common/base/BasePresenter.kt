package edu.tjrac.swant.baselib.common.base

import rx.Subscription
import java.util.*

/**
 * Created by wpc on 2018-08-31.
 */

abstract class BasePresenter {

    var subscriptions: ArrayList<Subscription>? = null

    fun addSubscription(objects: Subscription) {
        if (subscriptions == null) {
            subscriptions = ArrayList<Subscription>()
        }
        subscriptions!!.add(objects)
    }

    fun detachView() {
        if (subscriptions != null && subscriptions!!.size > 0) {
            for (item in subscriptions!!) {
                item.unsubscribe()
            }
        }
    }

}