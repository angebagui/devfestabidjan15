package org.gdgmiagegi.devfestabidjan15.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import org.gdgmiagegi.devfestabidjan15.R
import org.gdgmiagegi.devfestabidjan15.activity.SessionDetailActivity
import org.gdgmiagegi.devfestabidjan15.adapter.ScheduleAdapter
import org.gdgmiagegi.devfestabidjan15.adapter.ScheduleItemClickListener
import org.gdgmiagegi.devfestabidjan15.entity.BootstrapData
import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem
import org.gdgmiagegi.devfestabidjan15.util.extension.d
import org.gdgmiagegi.devfestabidjan15.util.extension.e
import org.gdgmiagegi.devfestabidjan15.util.extension.parseResource
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread
import java.io.IOException

/**
 * Created by macbookpro on 17/12/2015.
 */
class ScheduleFragment : Fragment(), ScheduleItemClickListener {

    var mScheduleItemClickListener : ScheduleItemClickListener?=null

    var scheduleRecyclerView:RecyclerView?=null

    var swipeRefreshLayout:SwipeRefreshLayout?=null

    val recyclerAdapater:ScheduleAdapter by lazy { ScheduleAdapter() }

    val TAG = "ScheduleFragment"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_schedule, container,false)

        scheduleRecyclerView = view?.find<RecyclerView>(R.id.schedule_recycler_view)
        swipeRefreshLayout = view?.find<SwipeRefreshLayout>(R.id.schedule_swipe_refresh_layout)
        mScheduleItemClickListener = this

        scheduleRecyclerView?.layoutManager = LinearLayoutManager(activity)


        recyclerAdapater.onItemClickListener = {schedule -> mScheduleItemClickListener?.navigateToSessionDetail(schedule)}
        scheduleRecyclerView?.adapter = recyclerAdapater
        swipeRefreshLayout?.isRefreshing =true
        loadBootStrapData()
        swipeRefreshLayout?.setOnRefreshListener {
            loadBootStrapData()
        }

        return view
    }

    private fun loadBootStrapData(){
            async {
                try{
                val jsonString = parseResource(activity.applicationContext, R.raw.bootstrap_data)
                jsonString?.let {
                    val jsonData = Gson().fromJson(jsonString, BootstrapData::class.java)
                    d(TAG, "jsonData --> "+jsonData)
                    val schedules = jsonData.getScheduleItemList()
                    uiThread {
                        recyclerAdapater.items = schedules
                        swipeRefreshLayout?.isRefreshing = false
                     }

                }
                }catch(ioex: IOException){
                        e(TAG,"ioexception ==> ", ioex)
                }

            }




    }



    override fun navigateToSessionDetail(schedule: ScheduleItem) {
       Log.d("ScheduleFragment", "--->>> $schedule")
        startActivity<SessionDetailActivity>(SessionDetailActivity.EXTRA_SCHEDULE to schedule)
    }




}