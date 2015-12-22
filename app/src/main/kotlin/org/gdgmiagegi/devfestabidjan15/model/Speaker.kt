package org.gdgmiagegi.devfestabidjan15.model

import java.io.Serializable

/**
 * Created by macbookpro on 17/12/2015.
 */
data class Speaker(val id:Int, val name:String, val title:String?, var company:String?=null, val badges: Badges?=null, val tag: List<String>,val photoUrl:String?=null , val country:String?=null, val twitterUrl:String?=null, val plusOneUrl:String?=null):Serializable