package edu.tjrac.swant.baselib.common.adapter

import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*

/**
 * Created by wpc on 2016/11/18.
 */
class FragmentsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var animate_switch=false

    val mFragments: MutableList<Fragment> = ArrayList()
    val mFragmentTitles: MutableList<String> = ArrayList()

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mFragmentTitles.add(title)
    }

    override fun getItem(i: Int): Fragment {
        return mFragments[i]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitles[position]
    }

    fun getFragmentByTitle(title: String): Fragment {
        val i = mFragmentTitles.indexOf(title)
        return mFragments[i]
    }

    fun setUpWithRadioGroup(vp: ViewPager, g: RadioGroup) {
        g.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until count) {
                if (checkedId == group.getChildAt(i).id) {
                    vp.setCurrentItem(i,animate_switch)
                }
            }
        }
    }

}