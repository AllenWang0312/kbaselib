package edu.tjrac.swant.baselib.common.base

/**
 * Created by wpc on 2018-08-31.
 */

abstract class BaseMVPFragment<P : BasePresenter> : BaseFragment(){
    protected lateinit var presenter : P

}