package com.accentrs.iofferbh.fragment


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.coupon.CouponLocationAdapter
import com.accentrs.iofferbh.model.coupon.Locations
import kotlinx.android.synthetic.main.fragment_location.*


/**
 * A simple [Fragment] subclass.
 */
class CouponLocationFragment : DialogFragment() {

    private var rootView: View? = null

    private var locationsArrayList: ArrayList<Locations>? = null

    private var rvLocation: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            locationsArrayList = arguments!!.getSerializable("locations") as ArrayList<Locations>
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_location, container, false)
            initializeViews()
            setDialog()
            setAdapter()
        }
        return rootView
    }

    private fun setDialog() {
        if (dialog.window != null)
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    }


    private fun initializeViews() {
        rvLocation = rootView?.findViewById(R.id.rv_location)
        //rv_location.layoutManager= GridLayoutManager(activity, 2)
        rvLocation?.layoutManager = LinearLayoutManager(activity)
    }


    private fun setAdapter() {
        rvLocation!!.adapter = CouponLocationAdapter(context, locationsArrayList)
    }

    companion object {

        val TAG = CouponLocationFragment::class.java.simpleName
    }
}
