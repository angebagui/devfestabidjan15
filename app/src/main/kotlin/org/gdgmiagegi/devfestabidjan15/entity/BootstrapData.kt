package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName
import org.gdgmiagegi.devfestabidjan15.model.Badges
import org.gdgmiagegi.devfestabidjan15.model.ScheduleItem
import org.gdgmiagegi.devfestabidjan15.model.Session
import org.gdgmiagegi.devfestabidjan15.model.Speaker

/**
 * Created by macbookpro on 17/12/2015.
 */
class BootstrapData (
    @SerializedName("date") val date:String?=null,
    @SerializedName("dateReadable") val dateReadable:String?=null,
    @SerializedName("schedule") val schedules:List<ScheduleData>?=null,
    @SerializedName("rooms") val rooms:List<RoomData>?=null,
    @SerializedName("sessions") val sessions:List<SessionData>?=null,
    @SerializedName("speakers") val speakers:List<SpeakerData>?=null,
    @SerializedName("parterns")val parters:List<PatnerData>?=null){

    public fun getScheduleItemList() = arrayListOf<ScheduleItem>().apply {
        schedules?.forEach { schedule ->
                    sessions?.find { it.id==schedule.session}?.let {  itSession ->
                        val speakersModel =  getListSpeaker(itSession?.speakers)
               add(ScheduleItem(schedule.startTime, schedule.endTime, itSession.title, Session(itSession.id,itSession.title,itSession.description,speakersModel,itSession.complexity,itSession.tags),itSession.speakers!=null))
           }

        }

    }

    private fun getListSpeaker(speakers:List<SpeakerData>?) = arrayListOf<Speaker>().apply {

        speakers?.forEach { itSpeaker->
                add(Speaker(itSpeaker.id,itSpeaker.name?:"",itSpeaker.title, itSpeaker.company, itSpeaker.badges,itSpeaker.tags?:listOf(),itSpeaker.photoUrl,itSpeaker.country, getTwitter(itSpeaker.socials),getPlusOne(itSpeaker.socials)))


       }
    }

    private fun getPlusOne(socials: List<SocialData>?): String? {
        if(socials !=null && socials.size>0){
            return socials?.find { it.name=="Google+" }?.link
        }else{
            return ""
        }
    }

    private fun getTwitter(socials: List<SocialData>?): String? {
          if(socials !=null && socials.size>0){
              return socials.find { it.name=="Twitter" }?.link
          }else{
              return ""
          }
    }


}