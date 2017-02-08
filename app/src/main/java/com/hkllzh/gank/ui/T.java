package com.hkllzh.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lizheng on 2017/2/8.
 */

public class T extends AppCompatActivity {

    private static final String TAG = "T";

    private Context mContext;

    public void d(Context context){
        mContext = context;
    }

    public void v() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(T.this, T.class);
                i.setAction("");

            }
        }, 1000);

//        //创建一个上游 Observable：
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("");
//            }
//        });
//
//        //创建一个下游 Observer
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "subscribe");
//            }
//
//            @Override
//            public void onNext(String value) {
//                Log.d(TAG, "" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "complete");
//            }
//        };
//
//        observable.subscribe(observer);

        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Wrold");
                subscriber.onCompleted();
            }
        });



    }
}
