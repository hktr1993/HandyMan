package code.challenge.handyman.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "handymanentity")
data class ProfilePicture(
    var large: String,
    var medium: String,
    var thumbnail: String,
) : Parcelable