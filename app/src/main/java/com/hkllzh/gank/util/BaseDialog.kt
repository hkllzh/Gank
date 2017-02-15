package com.hkllzh.gank.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.support.annotation.LayoutRes
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager

import com.hkllzh.gank.R

abstract class BaseDialog : Dialog {

    /**
     * 用一个默认样式文件

     * @param context     ctx
     * *
     * @param layoutResId 布局文件
     */
    constructor(context: Context, @LayoutRes layoutResId: Int) : super(context, R.style.AppTheme_BaseDialog) {
        init(layoutResId)
    }

    /**
     * @param context     ctx
     * *
     * @param layoutResId 布局文件
     * *
     * @param styleId     样式文件
     */
    constructor(context: Context, layoutResId: Int, styleId: Int) : super(context, styleId) {
        init(layoutResId)
    }

    private fun init(@LayoutRes layoutResId: Int) {
        setContentView(layoutResId)
        setProperty()
        initView()
        initData()
    }


    private fun setProperty() {

        val window = window
        val p = window!!.attributes
        p.x = 0
        p.y = 0
        val point = w_H
        p.width = point.x
        p.height = point.y

        window.attributes = p
    }

    /**
     * 控制整个dialog大小。默认为全屏展示此dialog<br></br>
     * 如需修改复写此方法即可

     * @return 要显示的宽高的值
     */
    protected val w_H: Point
        @SuppressLint("NewApi")
        get() {
            val point = Point()
            val d = window!!.windowManager.defaultDisplay

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
                point.x = d.height
                point.y = d.width
            } else {
                d.getSize(point)
            }
            return point
        }

    protected fun <T : View> `$`(id: Int): T {
        return findViewById(id) as T
    }

    protected abstract fun initView()

    protected abstract fun initData()
}