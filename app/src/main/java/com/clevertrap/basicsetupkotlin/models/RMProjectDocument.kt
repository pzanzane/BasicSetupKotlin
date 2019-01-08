package com.fidelitservices.brownstone.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class RMProjectDocument() : RMBase, Parcelable {

    @PrimaryKey
    @Required
    @SerializedName("id")
    var id: String? = ""

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("link")
    var link: String? = ""

    @SerializedName("createdAt")
    var createdAt: String? = ""

    var isChecked: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        link = parcel.readString()
        createdAt = parcel.readString()
        isChecked = parcel.readByte() != 0.toByte()
    }


//    override fun copy(): RMProjectDocument {
//
//        val rmDoc = RMProjectDocument()
//
//        rmDoc.id            = id
//        rmDoc.name          = name
//        rmDoc.link          = link
//        rmDoc.createdAt     = createdAt
//        rmDoc.isChecked     = isChecked
//        return rmDoc
//
//    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(link)
        parcel.writeString(createdAt)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        fun primaryKey(): String? {
            return "id"
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<RMProjectDocument> {
            override fun createFromParcel(parcel: Parcel): RMProjectDocument {
                return RMProjectDocument(parcel)
            }

            override fun newArray(size: Int): Array<RMProjectDocument?> {
                return arrayOfNulls(size)
            }
        }
    }


}