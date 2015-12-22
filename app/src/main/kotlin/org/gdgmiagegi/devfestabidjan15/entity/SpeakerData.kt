package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName
import org.gdgmiagegi.devfestabidjan15.model.Badges

/**
 * Created by macbookpro on 17/12/2015.
 */
data class SpeakerData(@SerializedName("id")val id:Int,
                       @SerializedName("name")val name:String?=null,
                       @SerializedName("title")val title:String?=null,
                       @SerializedName("company")val company:String?=null,
                       @SerializedName("country")val country:String?=null,
                       @SerializedName("photoUrl")val photoUrl:String?=null,
                       @SerializedName("bio")val bio:String?=null,
                       @SerializedName("badges") val badges: Badges?=null,
                       @SerializedName("tags")val tags:List<String>?=null,
                       @SerializedName("socials")val socials:List<SocialData>?=null)