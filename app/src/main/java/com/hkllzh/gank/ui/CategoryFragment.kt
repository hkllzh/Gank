package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment


/**
 * Created by lizheng on 2017/2/15.
 */
class CategoryFragment : Fragment() {


    companion object {
        fun newInstance(category: String): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }
}