package com.thinkitive.rmhscanner_android.sqlscanner


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thinkitive.rmhscanner_android.Models.ProcedureResponse
import com.thinkitive.rmhscanner_android.Models.RMHProduct
import com.thinkitive.rmhscanner_android.Models.spinnerInfoObj
import com.thinkitive.rmhscanner_android.PreferenceUtils
import com.thinkitive.rmhscanner_android.Utility.AppConstant
import com.thinkitive.rmhscanner_android.Utility.AppExecutor
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

object SQLConnector {


    private var DATABASENAME="rmhdb"
    private var USERNAME="sa"
    private var PASSWORD="123456"
    private var SERVERURL="192.168.2.116:42573"
    private var CONNECTIONSTRING = "jdbc:jtds:sqlserver://"+ SERVERURL+";"+"databaseName="+DATABASENAME

    //"jdbc:sqlserver://192.168.1.185:1995;databaseName="+DATABASENAME+";"
    //Samjay: 192.168.1.185:1995, databaseName = 420RMHDemo
    //jyoti: 192.168.2.130:49170, databaseName = rmhdb

    private var connection:Connection? = null



    fun isConnectedToDatabase(completion: (isConnected: Boolean) -> Unit) {

        AppExecutor.getSingleton().networkIO.execute{
            try {

                val statement = connection?.createStatement()
                val resultSet: java.sql.ResultSet? = statement?.executeQuery(QueryConstants.checkConnectionQuery())

                Log.d("DATABASE", "isConnectedToDatabase resultSet: " +resultSet )
                AppExecutor.getSingleton().mainThread.execute {
                    completion(resultSet != null)
                }
            }catch (e: SQLException){
                e.printStackTrace()
                Log.d("DATABASE", "Exception in isConnectedToDatabase" )
                completion(false)
            }
        }



    }

    fun disconnectDatabase(){
        if(connection != null && !connection!!.isClosed) {
            PreferenceUtils.getSingleton().remove(AppConstant.PreferenceConstant.PREF_DATABASENAME)
            PreferenceUtils.getSingleton().remove(AppConstant.PreferenceConstant.PREF_USERNAME)
            PreferenceUtils.getSingleton().remove(AppConstant.PreferenceConstant.PREF_PASSWORD)
            PreferenceUtils.getSingleton().remove(AppConstant.PreferenceConstant.PREF_SERVER_URL)
            connection!!.close()
        }
    }


    private fun connector(completion: (isConnected:Boolean) -> Unit){

        isConnectedToDatabase {
            status ->

            if (status) {
                Log.d("DATABASE", "isConnectedToDatabase: true" )
                completion(true)
            }else{
                DATABASENAME = PreferenceUtils.getSingleton().getString(AppConstant.PreferenceConstant.PREF_DATABASENAME)!!
                USERNAME = PreferenceUtils.getSingleton().getString(AppConstant.PreferenceConstant.PREF_USERNAME)!!
                PASSWORD = PreferenceUtils.getSingleton().getString(AppConstant.PreferenceConstant.PREF_PASSWORD)!!
                SERVERURL = PreferenceUtils.getSingleton().getString(AppConstant.PreferenceConstant.PREF_SERVER_URL)!!
                CONNECTIONSTRING = "jdbc:jtds:sqlserver://" + SERVERURL + ";" + "databaseName=" + DATABASENAME

                AppExecutor.getSingleton().networkIO.execute {

                    Log.d("DATABASE", "CONNECTIONSTRING: " + CONNECTIONSTRING)


                    try {
                        Class.forName("net.sourceforge.jtds.jdbc.Driver")
                        connection = DriverManager.getConnection(CONNECTIONSTRING, USERNAME, PASSWORD)
                        Log.d("DATABASE", "DATABASE Connection Successful")
                        AppExecutor.getSingleton().mainThread.execute {
                            completion(true)
                        }

                    }catch (sql: SQLException){
                        sql.printStackTrace()
                        Log.d("DATABASE", "EXCEPTION: " + sql.message)
                        AppExecutor.getSingleton().mainThread.execute {
                            completion(false)
                        }
                    }
                }
            }
        }

    }
    fun connect(): LiveData<SQLConnector> {


        val livedata: MutableLiveData<SQLConnector> = MutableLiveData()

        connector {isConnected ->

            if(isConnected){
                livedata.postValue(this)
            }else{
                livedata.postValue(null)
            }
        }

        return livedata

    }


    private fun executeQuery(sqlQuery: String, onComplete:(ResultSet?) -> Unit){

        AppExecutor.getSingleton().networkIO.execute{

            val statement = connection?.createStatement()
            val resultSet: java.sql.ResultSet? = statement?.executeQuery(sqlQuery)

            AppExecutor.getSingleton().mainThread.execute{
                onComplete(resultSet)
            }

        }

    }

    fun getDepartment(sqlQuery: String): LiveData<ArrayList<spinnerInfoObj>>{
        val liveData: MutableLiveData<ArrayList<spinnerInfoObj>> = MutableLiveData()


        executeQuery(sqlQuery) { resultSet ->

            val list: ArrayList<spinnerInfoObj> = ArrayList()

            AppExecutor.getSingleton().networkIO.execute {
                while (resultSet != null && resultSet.next()){

                    val spinnerInfoObj = spinnerInfoObj()
                    spinnerInfoObj.ID = resultSet?.getInt("ID")
                    spinnerInfoObj.Name = resultSet?.getString("Name")

                    list.add(spinnerInfoObj)
                }

                AppExecutor.getSingleton().mainThread.execute {

                    liveData.postValue(list)

                }
            }

        }


        return liveData
    }

    fun getCategory(sqlQuery: String): LiveData<ArrayList<spinnerInfoObj>>{
        val liveData: MutableLiveData<ArrayList<spinnerInfoObj>> = MutableLiveData()

        executeQuery(sqlQuery) { resultSet ->

            val list: ArrayList<spinnerInfoObj> = ArrayList()

            AppExecutor.getSingleton().networkIO.execute {
                while (resultSet != null && resultSet.next()){

                    val spinnerInfoObj = spinnerInfoObj()
                    spinnerInfoObj.ID = resultSet?.getInt("ID")
                    spinnerInfoObj.Name = resultSet?.getString("Name")

                    list.add(spinnerInfoObj)
                }

                AppExecutor.getSingleton().mainThread.execute {

                    liveData.postValue(list)

                }
            }

        }

        return liveData
    }

    fun getUOM(sqlQuery: String): LiveData<ArrayList<spinnerInfoObj>>{
        val liveData: MutableLiveData<ArrayList<spinnerInfoObj>> = MutableLiveData()

        executeQuery(sqlQuery) { resultSet ->

            val list: ArrayList<spinnerInfoObj> = ArrayList()

            AppExecutor.getSingleton().networkIO.execute {

                while (resultSet != null && resultSet.next()){

                    val spinnerInfoObj = spinnerInfoObj()
                    spinnerInfoObj.ID = resultSet?.getInt("ID")
                    spinnerInfoObj.Name = resultSet?.getString("Name")

                    list.add(spinnerInfoObj)
                }


                AppExecutor.getSingleton().mainThread.execute {

                    liveData.postValue(list)

                }
            }

        }

        return liveData
    }

    fun getAllProducts(sqlQuery: String): LiveData<ArrayList<RMHProduct>>{

        val liveData: MutableLiveData<ArrayList<RMHProduct>> = MutableLiveData()

        executeQuery(sqlQuery){
                resultSet ->


            val list: ArrayList<RMHProduct> = ArrayList()

            AppExecutor.getSingleton().networkIO.execute {

                while (resultSet != null && resultSet.next()){

                    val rmhProduct = RMHProduct()
                    rmhProduct.ID = resultSet.getInt("ID")
                    rmhProduct.Department = resultSet.getString("Department")
                    rmhProduct.DepartmentID = resultSet.getInt("DepartmentID")
                    rmhProduct.Description = resultSet.getString("Description")
                    rmhProduct.ItemLookupCode = resultSet.getString("ItemLookupCode")
                    rmhProduct.UnitOfMeasure = resultSet.getString("UnitOfMeasure")
                    rmhProduct.Qty = resultSet.getInt("Qty")
                    rmhProduct.Price = resultSet.getDouble("Price")
                    rmhProduct.Cost = resultSet.getDouble("Cost")
                    rmhProduct.ProductId = resultSet.getString("ProductId")
                    rmhProduct.isCannabis = resultSet.getBoolean("isCannabis")
                    rmhProduct.Weight = resultSet.getDouble("Weight")
                    rmhProduct.category = resultSet.getString("category")
                    rmhProduct.CategoryID = resultSet.getInt("CategoryID")

                    list.add(rmhProduct)
                }

                AppExecutor.getSingleton().mainThread.execute {

                    liveData.postValue(list)

                }
            }
        }


        return liveData
    }

    fun execProcedure(execProcedure:String): LiveData<ProcedureResponse>{

        Log.d("HH",execProcedure);


        val liveData: MutableLiveData<com.thinkitive.rmhscanner_android.Models.ProcedureResponse> = MutableLiveData()

        Log.d("PROCEDURE","==========================")
        Log.d("PROCEDURE","Procedure: "+ execProcedure)
        executeQuery(execProcedure){
            resultSet ->

            while (resultSet != null && resultSet.next()){

                Log.d("PROCEDURE","status: "+ resultSet.getBoolean("Status"))
                Log.d("PROCEDURE","ErrorMsg: "+ resultSet.getString("ErrorMsg"))
                Log.d("PROCEDURE","ID: " +resultSet.getString("ID"))

                val procedureResponse = ProcedureResponse()
                procedureResponse.status = resultSet.getBoolean("Status")
                procedureResponse.errorMsg = resultSet.getString("ErrorMsg")
                procedureResponse.id =  if(procedureResponse.status) resultSet.getInt("ID") else 0



                liveData.postValue(procedureResponse)

            }
        }

        return liveData
    }

}