package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by macbookpro on 17/12/2015.
 */

data class SocialData(
        @SerializedName("name")val name:String,
        @SerializedName("link")val link:String)