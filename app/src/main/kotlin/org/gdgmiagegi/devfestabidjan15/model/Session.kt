package org.gdgmiagegi.devfestabidjan15.model

import java.io.Serializable

/**
 * Created by macbookpro on 17/12/2015.
 */
data class Session(val id:Int, val title:String, val description:String, val speakers:List<Speaker>, val complexity:String?, val tags:List<String>?):Serializable