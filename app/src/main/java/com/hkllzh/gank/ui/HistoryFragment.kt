package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment


/**
 * Created by lizheng on 2017/2/15.
 */
class HistoryFragment : Fragment() {

    companion object {
        fun newInstance(): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}