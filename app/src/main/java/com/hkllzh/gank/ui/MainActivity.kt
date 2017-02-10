package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.VISIBLE
import android.widget.TextView
import com.hkllzh.gank.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val v = findViewById(R.id.tvTest) as TextView
        v.visibility = VISIBLE
        v.text = getString(R.string.hello)
    }
}