package org.gdgmiagegi.devfestabidjan15.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by macbookpro on 17/12/2015.
 */
data class PatnerData(
        @SerializedName("title")val title:String,
        @SerializedName("logos")val logos:List<Logo>)