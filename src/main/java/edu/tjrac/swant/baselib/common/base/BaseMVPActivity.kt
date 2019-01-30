package edu.tjrac.swant.baselib.common

/**
 * Created by wpc on 2018-08-31.
 */

abstract class BaseMVPActivity<P : BasePresenter> : BaseActivity() {
    protected var presenter: P? = null

    override fun showInfoDialog() {
        super.showInfoDialog()
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }
}