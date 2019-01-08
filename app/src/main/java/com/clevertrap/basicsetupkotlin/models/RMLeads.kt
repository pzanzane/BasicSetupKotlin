package com.fidelitservices.brownstone.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class RMLeads() : RMBase, Parcelable {

    @PrimaryKey
    @Required
    @SerializedName("id")
    var id: String? = ""

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("channel")
    var channel: String? = ""

    @SerializedName("project")
    var project: String? = ""

    @SerializedName("status")
    var status: String? = ""

    @SerializedName("mobile")
    var mobile: String? = ""

    @SerializedName("email")
    var email: String? = ""

    @SerializedName("sms")
    var isSmsSent: Boolean = false

    @SerializedName("mail")
    var isEmailSent: Boolean = false


    @SerializedName("configuration")
    var configuration: String? = ""

    @SerializedName("budget")
    var budget: String? = ""

    @SerializedName("hotness")
    var hotness: String? = ""

    @SerializedName("junkreason")
    var junkreason: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        channel = parcel.readString()
        project = parcel.readString()
        status = parcel.readString()
        mobile = parcel.readString()
        email = parcel.readString()
        isSmsSent = parcel.readByte() != 0.toByte()
        isEmailSent = parcel.readByte() != 0.toByte()
        configuration = parcel.readString()
        budget = parcel.readString()
        hotness = parcel.readString()
        junkreason = parcel.readString()
    }

    companion object {
        fun primaryKey(): String? {
            return "id"
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<RMLeads> {
            override fun createFromParcel(parcel: Parcel): RMLeads {
                return RMLeads(parcel)
            }

            override fun newArray(size: Int): Array<RMLeads?> {
                return arrayOfNulls(size)
            }
        }
    }

//    override fun copy(): RMLeads {
//
//        val rmLead = RMLeads()
//
//        rmLead.id           = id
//        rmLead.name         = name
//        rmLead.mobile       = mobile
//        rmLead.email        = email
//        rmLead.channel      = channel
//        rmLead.id           = id
//        rmLead.status       = status
//        rmLead.project      = project
//        rmLead.isEmailSent  = isEmailSent
//        rmLead.isSmsSent    = isSmsSent
//        rmLead.configuration= configuration
//        rmLead.budget       = budget
//        rmLead.hotness      = hotness
//        return rmLead
//
//    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(channel)
        parcel.writeString(project)
        parcel.writeString(status)
        parcel.writeString(mobile)
        parcel.writeString(email)
        parcel.writeByte(if (isSmsSent) 1 else 0)
        parcel.writeByte(if (isEmailSent) 1 else 0)
        parcel.writeString(configuration)
        parcel.writeString(budget)
        parcel.writeString(hotness)
        parcel.writeString(junkreason)
    }

    override fun describeContents(): Int {
        return 0
    }

//    companion object CREATOR : Parcelable.Creator<RMLeads> {
//        override fun createFromParcel(parcel: Parcel): RMLeads {
//            return RMLeads(parcel)
//        }
//
//        override fun newArray(size: Int): Array<RMLeads?> {
//            return arrayOfNulls(size)
//        }
//    }


}