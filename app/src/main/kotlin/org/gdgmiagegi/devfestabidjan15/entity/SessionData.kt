package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by macbookpro on 17/12/2015.
 */
data class SessionData (@SerializedName("id")val id:Int, @SerializedName("title")val title:String, @SerializedName("description") val description:String, @SerializedName("speakers") val speakers: List<SpeakerData>?=null, @SerializedName("language")val language: String, @SerializedName("complexity")val complexity:String, @SerializedName("tags") val tags:List<String>)