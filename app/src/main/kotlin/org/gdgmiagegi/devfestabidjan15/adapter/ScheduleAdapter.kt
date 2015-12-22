package org.gdgmiagegi.devfestabidjan15.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.gdgmiagegi.devfestabidjan15.R
import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem
import org.gdgmiagegi.devfestabidjan15.util.extension.LIVE_DAY
import org.gdgmiagegi.devfestabidjan15.util.extension.isLive
import org.jetbrains.anko.find
import kotlin.properties.Delegates
import org.gdgmiagegi.devfestabidjan15.util.extension.live


/**
 * Created by macbookpro on 17/12/2015.
 */
class ScheduleAdapter():RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    var items:List<ScheduleItem> by Delegates.observable(emptyList()){prop,old,new -> notifyDataSetChanged()}

    var onItemClickListener: ((ScheduleItem) -> Unit)? = null
    companion object{
        val TAG :String= "ScheduleAdapter"
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScheduleViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.schedule_item_list, parent,false)
        return ScheduleViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder?, position: Int) {
       holder?.setItem(items[position])
    }

    override fun getItemCount() = items.size

    class ScheduleViewHolder(itemView: View, var onItemClickListener: ((ScheduleItem) -> Unit)?):RecyclerView.ViewHolder(itemView){

        private val start_time :TextView = itemView.find(R.id.start_time)
        private val browse_sessions:TextView = itemView.find(R.id.browse_sessions)
        private val slot_title:TextView = itemView.find(R.id.slot_title)
        private val slot_description:TextView = itemView.find(R.id.slot_description)
        private val live_now_badge:TextView = itemView.find(R.id.live_now_badge)

        fun setItem(scheduleItem: ScheduleItem){
            start_time.text = scheduleItem.startTime

            if(scheduleItem.isSession){
                slot_title.text = scheduleItem.session?.title
                //9:00 AM - 11:30 AM / Room 1
                slot_description.text = scheduleItem.startTime.concat(" - ").concat(scheduleItem.endTime).concat(" / ").concat(scheduleItem.session?.description)
                itemView?.setOnClickListener { view -> onItemClickListener?.invoke(scheduleItem) }
                browse_sessions.visibility = View.VISIBLE
            }else{
                browse_sessions.visibility = View.GONE
                slot_title.text = scheduleItem.title
                slot_description.text = scheduleItem.startTime.concat(" - ").concat(scheduleItem.endTime)
            }


            scheduleItem.live(LIVE_DAY,{
                live_now_badge.visibility =View.VISIBLE
            },{
                live_now_badge.visibility =View.GONE
            })


        }
    }
}