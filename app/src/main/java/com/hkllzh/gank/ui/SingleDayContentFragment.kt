package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hkllzh.gank.R
import com.hkllzh.gank.net.APIManager
import com.hkllzh.gank.net.GankApi
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SingleDayContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SingleDayContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleDayContentFragment : Fragment() {

    private val TAG = "SingleDayContentFrg"

    private var tvTest: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_content, container, false)
        tvTest = v.findViewById(R.id.tvTest) as TextView
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        APIManager.getApi(GankApi::class.java).category("Android", 2, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { jsonObject ->
                            Log.d(TAG, "onNext-->" + jsonObject.toString())
                        },
                        {
                            Log.d(TAG, "onError-->" + it)
                            it?.printStackTrace()
                        },
                        {
                            Log.d(TAG, "onCompleted-->")
                        }
                )

        APIManager.getApi(GankApi::class.java).haveDataHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, it.toString())
                    tvTest?.text = it.toString()
                }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): SingleDayContentFragment {
            val fragment = SingleDayContentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
