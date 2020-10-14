package code.challenge.handyman.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
    var latitude: Double,
    var longitude: Double
) : Parcelable