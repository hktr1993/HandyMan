package code.challenge.handyman.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var street: String,
    var city: String,
    var state: String,
    var postalCode: String,
    var country: String
) : Parcelable