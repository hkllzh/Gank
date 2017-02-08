package com.hkllzh.gank

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hkllzh.gank.study.StudyRxJava
import com.hkllzh.gank.ui.MainActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * 启动页面
 *
 * 延时2秒进入主页面
 */
class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        Handler().postDelayed({
//            val i = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(i)
//        }, 2000)

        // studyRxJava()

        // 2秒后执行一个动作
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                })
    }

    private fun studyRxJava() {
        val rxJava = StudyRxJava()
        rxJava.demo(this)
    }
}
