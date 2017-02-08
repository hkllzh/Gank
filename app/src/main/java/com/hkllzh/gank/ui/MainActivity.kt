package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.hkllzh.gank.R
import com.hkllzh.gank.testDataClass

object Global {
    val VIEW_VISIBLE = VISIBLE
    val VIEW_GONE = GONE
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val v: TextView = findViewById(R.id.tvTest) as TextView
        v.visibility = Global.VIEW_VISIBLE
        // v.text = testGetString("fanny".hello("lizheng"))
        v.text = testDataClass(1, "fanny".hello("lizheng"))
    }

    fun String.hello(name: String): String {
        return "Welcome " + this + " and $name"
    }
}
