package edu.tjrac.swant.baselib.common.base

/**
 * Created by wpc on 2018-09-07.
 */

open class BaseBarMVPActivity<P : BasePresenter> : BaseBarActivity(){
    protected var presenter: P? = null

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }
}
