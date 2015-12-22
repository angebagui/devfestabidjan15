package org.gdgmiagegi.devfestabidjan15.util.extension

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem
import java.io.*
import java.sql.Timestamp
import java.util.*


/**
 * Created by macbookpro on 17/12/2015.
 */

val LIVE_DAY = 19
fun d(tag:String,message:String) = Log.d(tag, message)
fun e(tag:String,message:String, tr:Throwable) = Log.e(tag,message,tr)
fun v(tag:String,message:String) = Log.v(tag, message)

fun AppCompatActivity.snackbar(view: View, text:String, duration:Int=Snackbar.LENGTH_LONG) = Snackbar.make(view,text,duration)

@Throws(IOException::class)
fun parseResource(context: Context, ressource:Int): String {
    val inputStream = context.resources.openRawResource(ressource)
    val sb=StringBuilder()
    val br = BufferedReader(InputStreamReader(inputStream))
    var read = br.readLine()

    while(read!= null) {
        sb.append(read);
        read = br.readLine()

    }

    return sb.toString();
}

 fun ScheduleItem.live(day :Int ,onlive:()->Unit, notlive:()->Unit){
    if(isLive(day))
        onlive()
    else
        notlive()

}



fun ScheduleItem.isLive(day :Int):Boolean{
    val date = Date(System.currentTimeMillis())
    val currentTimestamp = Timestamp(date.time)
    val time = date.hours
    val currentDay = date.day
    val currentYear = date.year
    d("FunctionUtils", " $currentYear")
    if(day==LIVE_DAY){
        val startTimestamp = Timestamp.valueOf("2015-12-$day $startTime:00.00000000")
        val endTimestamp = Timestamp.valueOf("2015-12-$day $endTime:00.00000000")
        return currentTimestamp.time in (startTimestamp.time ..endTimestamp.time)
    }else
        return false


}