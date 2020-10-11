package com.h.business.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.h.business.R
import com.h.business.data.Rest
import kotlinx.android.synthetic.main.rest_list_item.view.*


class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.RestHolder>() {
    private  val TAG = "RecyclerAdapter"
    private var rests: List<Rest> = ArrayList()
    private var restsOld: List<Rest> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rest_list_item, parent, false)
        return RestHolder(itemView)

    }

    override fun getItemCount(): Int {
        return rests.size
    }

    override fun onBindViewHolder(holder: RestHolder, position: Int) {
        val currentRest: Rest = rests.get(position)
        holder.textViewName.text = currentRest.name
        holder.textDistance.text = currentRest.distance.toInt().toString() + "m"

        val progress = CircularProgressDrawable(holder.itemView.context)
        progress.strokeWidth = 10f
        progress.centerRadius = 20f
        progress.start()

        val requestOptions = RequestOptions()
            .placeholder(R.color.white)
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(currentRest.rest_image[0])
            .placeholder(progress)
            .into(holder.imageRest)
    }

    fun setRestsInAdapter(rests: List<Rest>) {
        restsOld=this.rests
        this.rests=rests
        updateList(restsOld,this.rests)
        notifyDataSetChanged()
    }

    private fun updateList(old:List<Rest>, new:List<Rest>) {
        val callback = DiffCallback(old, new)
        DiffUtil.calculateDiff((callback)).dispatchUpdatesTo(this)
    }

    class RestHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var textDistance: TextView
        var imageRest: ImageView

        init {
            textViewName = itemView.rest_name
            textDistance = itemView.distanse
            imageRest = itemView.rest_image
        }

    }
}