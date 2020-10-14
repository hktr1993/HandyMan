package code.challenge.handyman.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @Embedded
    var address: Address,
    @Embedded
    var coordinate: Coordinate
) : Parcelable