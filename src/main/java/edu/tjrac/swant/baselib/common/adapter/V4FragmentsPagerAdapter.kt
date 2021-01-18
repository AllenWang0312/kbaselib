package edu.tjrac.swant.baselib.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.widget.RadioGroup
import java.util.*

/**
 * Created by wpc on 2016/11/18.
 */
class V4FragmentsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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
                    vp.setCurrentItem(i,false)
                }
            }
        }
    }
    fun setUpWithRadioGroup(vp: ViewPager, g: RadioGroup, skip :Int) {
        g.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until count) {
                var position=i
                if(i>=skip){
                    position-=1
                }
                if (checkedId == group.getChildAt(i).id) {
                    vp.setCurrentItem(position,false)
                }
            }
        }
    }
//    fun setUpWithRadioButtons(vp: ViewPager, buttons: Array<RadioButton>) {
//        g.setOnCheckedChangeListener { group, checkedId ->
//            for (i in 0 until count) {
//                if (checkedId == group.getChildAt(i).id) {
//                    vp.setCurrentItem(i,false)
//                }
//            }
//        }
//    }
}