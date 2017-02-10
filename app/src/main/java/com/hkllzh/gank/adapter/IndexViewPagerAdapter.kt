package com.hkllzh.gank.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.hkllzh.gank.db.TabFragmentBean
import java.util.*

class IndexViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var tabFrgs: ArrayList<TabFragmentBean>? = null

    override fun getItem(position: Int): Fragment? {
        return tabFrgs!![position].frg
    }

    override fun getCount(): Int {
        if (null == tabFrgs) {
            return 0
        }
        return tabFrgs!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabFrgs!![position].tabTitle
    }
}
