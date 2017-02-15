package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment


/**
 * Created by lizheng on 2017/2/15.
 */
class FavFragment : Fragment() {



    companion object {
        fun newInstance(): FavFragment {
            val fragment = FavFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}