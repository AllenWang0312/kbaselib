package edu.tjrac.swant.baselib.common.adapter

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by Administrator on 2017/11/23 0023.
 */

class ViewsPagerAdapter : PagerAdapter() {

    internal var views: MutableList<View> = ArrayList()
    internal var titles: MutableList<String> = ArrayList()

    fun addView(item: View, title: String) {
        views.add(item)
        titles.add(title)
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
        //        super.destroyItem(container, position, object);
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
        //        return super.instantiateItem(container, position);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun  getItemSize(): Int {
        return views.size
    }
}
