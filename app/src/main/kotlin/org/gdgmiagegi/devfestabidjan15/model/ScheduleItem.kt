package org.gdgmiagegi.devfestabidjan15.model

import java.io.Serializable

/**
 * Created by macbookpro on 17/12/2015.
 */
 data class ScheduleItem(val startTime:String,val endTime:String, val title:String?=null,val session:Session, var isSession: Boolean=true):Serializable