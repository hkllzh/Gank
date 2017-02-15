package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hkllzh.gank.R
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by lizheng on 2017/2/15.
 */
open abstract class BaseListFragment : Fragment() {

    protected var recyclerView: RecyclerView by Delegates.notNull()
    protected var swipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull()
    protected val mItem = Items()
    protected var mAdapter: MultiTypeAdapter? = null
    protected var tvNoDataTip: TextView by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_content, container, false)
        recyclerView = v.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
            Observable.timer(300, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        swipeRefreshLayout.isRefreshing = false
                    }
        }

        tvNoDataTip = v.findViewById(R.id.tvNoDataTip) as TextView


        mAdapter = MultiTypeAdapter()
        registerAdapterItem()
        recyclerView.adapter = mAdapter

        haveData()
        return v
    }

    protected fun haveData() {
        tvNoDataTip.visibility = View.GONE
    }

    protected fun noData(tipInfo: String) {
        tvNoDataTip.visibility = View.VISIBLE
        tvNoDataTip.text = tipInfo
    }

    abstract fun registerAdapterItem()
    abstract fun onRefresh()

}