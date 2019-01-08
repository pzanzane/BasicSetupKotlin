package com.clevertrap.basicsetupkotlin.realmhelpers

import com.fidelitservices.brownstone.models.RMLeads
import com.clevertrap.basicsetupkotlin.utility.AppExecutor
import io.realm.kotlin.where

class RealmHelperLeads {

    fun getFreshLeads(complete: (list: List<RMLeads>?)->Unit) {
        RealmManager.getSinglton {
            realmManager ->

            AppExecutor.getSingleton().executeOnDatabaseThread {

                var realmQuery = realmManager.getQueryObject<RMLeads>()
                realmQuery  = realmQuery.`in`("status", arrayOf("FRESH"))

                realmManager.readFiltered(realmQuery, completed = {
                    list ->
                    complete(list)
                })
            }
        }
    }

    fun getClaimedLeads(complete: (list: List<RMLeads>?)->Unit) {
        RealmManager.getSinglton {
            realmManager ->

            AppExecutor.getSingleton().executeOnDatabaseThread {

                var realmQuery = realmManager.getQueryObject<RMLeads>()
                realmQuery  = realmQuery.`in`("status", arrayOf("CLAIMED"))

                realmManager.readFiltered(realmQuery, completed = {
                    list ->
                    complete(list)
                })
            }
        }
    }

    fun getJUNKLeads(complete: (list: List<RMLeads>?)->Unit) {
        RealmManager.getSinglton {
            realmManager ->

            AppExecutor.getSingleton().executeOnDatabaseThread {

                var realmQuery = realmManager.getQueryObject<RMLeads>()
                realmQuery  = realmQuery.`in`("status", arrayOf("JUNK"))

                realmManager.readFiltered(realmQuery, completed = {
                    list ->
                    complete(list)
                })
            }
        }
    }

}