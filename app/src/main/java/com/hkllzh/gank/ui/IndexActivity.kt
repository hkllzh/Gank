package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import com.hkllzh.gank.R
import com.hkllzh.gank.adapter.IndexViewPagerAdapter
import com.hkllzh.gank.bean.TabFragmentBean
import com.hkllzh.gank.db.HistoryDataDB
import com.hkllzh.gank.event.GetAllDate
import com.hkllzh.gank.net.haveDataHistory
import com.hkllzh.gank.util.RxBus
import java.util.*

class IndexActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val debug = true
    private val TAG = "IndexActivity"
    private val tabFrgs = ArrayList<TabFragmentBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)


        val tabLayout = findViewById(R.id.tabLayout) as TabLayout

        tabFrgs.add(TabFragmentBean("收藏", SingleDayContentFragment.newInstance()))
        tabFrgs.add(TabFragmentBean("今日", SingleDayContentFragment.newInstance()))
        tabFrgs.add(TabFragmentBean("历史", SingleDayContentFragment.newInstance()))
        tabFrgs.add(TabFragmentBean("Android", SingleDayContentFragment.newInstance()))

        val viewPager = findViewById(R.id.viewPager) as ViewPager
        val viewPagerAdapter = IndexViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.tabFrgs = tabFrgs
        viewPager.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(1)?.select()

        // 获取所有的日期
        haveDataHistory().subscribe {
            val lastDate = HistoryDataDB.newInstance()?.latest()
            if (debug) {
                Log.d(TAG, "lastDate = $lastDate")
            }

            // 数据库还没有存储
            if (TextUtils.isEmpty(lastDate)) {
                HistoryDataDB.newInstance()?.addAll(it)
            }

            // 数据库存储的和最新的不匹配
            if (!lastDate.equals(it[0])) {
                HistoryDataDB.newInstance()?.addAll(it)
            }

            // 通知需要刷新的地方
            RxBus.getDefault().post(GetAllDate())
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
