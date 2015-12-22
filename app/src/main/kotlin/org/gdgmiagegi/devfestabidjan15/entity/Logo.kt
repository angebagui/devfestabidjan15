package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by macbookpro on 17/12/2015.
 */
data class Logo (@SerializedName("name")val name:String, @SerializedName("url")val url:String, @SerializedName("logoUrl")val logoUrl:String)