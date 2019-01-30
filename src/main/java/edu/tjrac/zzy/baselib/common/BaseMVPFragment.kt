package edu.tjrac.swant.kotlin.baselib.common

/**
 * Created by wpc on 2018-08-31.
 */

abstract class BaseMVPFragment<P :BasePresenter> :BaseFragment(){
    protected lateinit var presenter : P

}