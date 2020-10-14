package code.challenge.handyman.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import code.challenge.handyman.Converter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "handymanentity")
data class HandyMan(
    @PrimaryKey
    var identifier: Int,
    var visitOrder: Int,
    var name: String,
    var phoneNumber: String,
    @Embedded
    var profilePicture: ProfilePicture,
    @Embedded
    var location: Location,
    var serviceReason: String,
    @TypeConverters(Converter::class)
    var problemPictures: List<String>
) : Parcelable