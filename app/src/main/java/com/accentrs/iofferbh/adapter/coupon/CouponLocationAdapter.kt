package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.model.coupon.Locations
import com.accentrs.iofferbh.viewholder.location.LocationViewHolder
import java.util.*

class CouponLocationAdapter(private val mContext: Context?, private val locationsItemArrayList: ArrayList<Locations>?) : RecyclerView.Adapter<LocationViewHolder>() {
    private var view: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.row_location_layout, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.tvLocationAddress.text = locationsItemArrayList!![position].address
        holder.tvLocationName.text = locationsItemArrayList[position].name
        holder.cvLocation.setOnClickListener { callMapIntent(locationsItemArrayList[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return locationsItemArrayList?.size ?: 0
    }


    private fun callMapIntent(locationsItem: Locations) {

        val locationLat = locationsItem.lat
        val locationLng = locationsItem.lng

        val strUri = "http://maps.google.com/maps?q=loc:" + locationLat + "," + locationLng + " (" + locationsItem.name + " " + locationsItem.address + ")"
        val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri))

        intent.setPackage("com.google.android.apps.maps")
        mContext?.startActivity(intent)
    }


}
