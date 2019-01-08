package com.clevertrap.basicsetupkotlin.realmhelpers

import com.clevertrap.basicsetupkotlin.application.ApplicationBasicSetupKotlin
import com.clevertrap.basicsetupkotlin.utility.AppExecutor
import com.fidelitservices.brownstone.models.RMBase
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import io.realm.kotlin.where

class RealmManager {

    companion object {

        private val realmName = "BrownStone"
        private val realmVersion = 1L

        private var INSTANCE: RealmManager? = null
        fun getSinglton(completed: (realmManager: RealmManager) -> Unit) {

                if (INSTANCE != null) {

                    AppExecutor.getSingleton().mainThread.execute {
                        completed(INSTANCE!!)
                    }
                }else{

                    synchronized(this){
                        if (INSTANCE != null) {
                            AppExecutor.getSingleton().mainThread.execute {
                                completed(INSTANCE!!)
                            }
                        }else{
                            AppExecutor.getSingleton().dbIO.execute {
                                INSTANCE = RealmManager()
                                AppExecutor.getSingleton().mainThread.execute {
                                    completed(INSTANCE!!)
                                }
                            }
                        }

                    }

                }
        }

    }

    private constructor() {

        Realm.init(ApplicationBasicSetupKotlin.applicationContext())
        val config = RealmConfiguration.Builder().name(realmName)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(realmVersion).build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()
    }
    public var realm: Realm? = null

    fun <T: RMBase>write(obj: T, completed: () -> Unit){

        AppExecutor.getSingleton().dbIO.execute {

            realm!!.executeTransaction{
                realm!!.copyToRealmOrUpdate(obj)
            }

            AppExecutor.getSingleton().mainThread.execute {
                completed()
            }
        }
    }

    fun <T: RMBase>blockingWriteAll(list: List<T>){


        realm!!.executeTransaction {
            realm!!.copyToRealmOrUpdate(list)
        }
    }

    fun <T: RMBase>blockingWrite(model: T){


        realm!!.executeTransaction {
            realm!!.copyFromRealm(model)
        }
    }

    fun <T: RMBase>writeAll(list: List<T>, completed: () -> Unit){


        AppExecutor.getSingleton().dbIO.execute {
            realm!!.executeTransaction {
                realm!!.copyToRealmOrUpdate(list)
            }

            AppExecutor.getSingleton().mainThread.execute {
                completed()
            }
        }
    }

    inline fun <reified T: RMBase>readAll(): List<T>?{

        var list: List<T>? = null
        realm!!.executeTransaction{
            transition ->
            list = transition.where<T>().findAll() as List<T>?
        }

        return list
    }

    inline fun <reified T: RMBase>deleteAll(cls: Class<T>,completed: () -> Unit) {

        AppExecutor.getSingleton().executeOnDatabaseThread {

            realm!!.executeTransaction{
                transition ->
                transition.delete(cls)
            }
        }
    }

    inline fun <reified T: RMBase>blockingDeleteAll(cls: Class<T>) {

        realm!!.executeTransaction{
            transition ->
            transition.delete(cls)
        }
    }
    inline fun <reified T: RMBase>readAll(crossinline completed: (list: List<T>?) -> Unit){

        AppExecutor.getSingleton().executeOnDatabaseThread {

            var listMutalbe: MutableList<T>? = null
            var list: List<T>? = null

            realm!!.executeTransaction{
                transition ->
                list = transition.where<T>().findAll()
            }
            if (list == null) {
                AppExecutor.getSingleton().mainThread.execute {
                    completed(listMutalbe)
                }

            }else{

                listMutalbe = mutableListOf()
                realm!!.executeTransaction {

                    transition ->

                    for(item in list!!){
                        listMutalbe.add(transition.copyFromRealm(item))
                    }

                    AppExecutor.getSingleton().mainThread.execute {
                        completed(listMutalbe)
                    }
                }

            }


        }

    }

    inline fun <reified T: RMBase>readFiltered(predicate: RealmQuery<T>,
                                               crossinline completed: (list: List<T>?) -> Unit){

        AppExecutor.getSingleton().executeOnDatabaseThread {

            var listMutalbe: MutableList<T>? = null
            var list: List<T>? = null

            //"status", arrayOf("fresh")
            realm!!.executeTransaction{
                transition ->
                list = predicate.findAll()

            }
            if (list == null) {
                AppExecutor.getSingleton().mainThread.execute {
                    completed(listMutalbe)
                }

            }else{

                listMutalbe = mutableListOf()
                realm!!.executeTransaction {

                    transition ->

                    for(item in list!!){
                        listMutalbe.add(transition.copyFromRealm(item))
                    }

                    AppExecutor.getSingleton().mainThread.execute {
                        completed(listMutalbe)
                    }
                }
            }


        }

    }

    inline fun <reified T: RMBase>findByPrimaryKey(primaryField: String,primaryKeyValue: String,
                                               crossinline completed: (obj: T?) -> Unit){

        AppExecutor.getSingleton().executeOnDatabaseThread {

            var obj: T? = null

            //"status", arrayOf("fresh")
            realm!!.executeTransaction{
                transition ->
                obj = transition.where(T::class.java)
                        .equalTo(primaryField, primaryKeyValue)
                        .findFirst()


            }
            if (obj == null) {
                AppExecutor.getSingleton().mainThread.execute {
                    completed(obj)
                }

            }else{

                realm!!.executeTransaction {
                    transition ->
                    obj = transition.copyFromRealm(obj)
                }

                AppExecutor.getSingleton().mainThread.execute {
                    completed(obj)
                }

            }


        }

    }

    inline fun <reified T: RMBase>getQueryObject(): RealmQuery<T> {
        return realm!!.where()
    }
}