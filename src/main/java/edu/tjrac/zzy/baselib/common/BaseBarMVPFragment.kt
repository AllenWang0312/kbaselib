package edu.tjrac.swant.kotlin.baselib.common

/**
 * Created by wpc on 2018-08-31.
 */

abstract class BaseBarMVPFragment<P :BasePresenter> :BaseBarFragment(){
    protected var presenter : P?=null

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

}