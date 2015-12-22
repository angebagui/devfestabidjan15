package org.gdgmiagegi.devfestabidjan15.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by macbookpro on 17/12/2015.
 */

data class Badges(@SerializedName("name")val name:String, @SerializedName("description")val description:String):Serializable