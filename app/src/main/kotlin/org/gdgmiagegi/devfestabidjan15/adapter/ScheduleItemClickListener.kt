package org.gdgmiagegi.devfestabidjan15.adapter;

import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem;

import kotlin.Unit;

interface ScheduleItemClickListener{
    fun navigateToSessionDetail(schedule:ScheduleItem):Unit

}