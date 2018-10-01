package com.diegoblajackis.uapp.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class User: Parcelable {
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var thumbnail: String? = null
    var picture: String? = null
    var age : String? =null
    var phone : String? =null
    var city : String? =null
    var street : String? =null
    var state : String? = null
}