package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by macbookpro on 17/12/2015.
 */

data class ScheduleData(@SerializedName("startTime")val startTime:String, @SerializedName("endTime")val endTime:String, @SerializedName("session")val session:Int)