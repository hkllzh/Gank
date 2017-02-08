package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.hkllzh.gank.R

object Global {
    val VIEW_VISIBLE = VISIBLE
    val VIEW_GONE = GONE
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val v = findViewById(R.id.tvTest) as TextView
        v.visibility = Global.VIEW_VISIBLE
        v.text = getString(R.string.hello)
    }
}
