package com.thinkitive.rmhscanner_android.Utility

import android.os.Handler
import android.os.Looper
import android.util.Log

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory


class AppExecutor private constructor(val diskIO: Executor,
                                      val networkIO: Executor,
                                      val dbIO: Executor,
                                      val mainThread: Executor) {

    interface IExecutorFinished {
        fun executorFinished()
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {

        private var INSTANCE: AppExecutor? = null

            fun getSingleton(): AppExecutor {

                val instance: AppExecutor? = INSTANCE

                if (instance != null) {
                    return instance
                }

                synchronized(this) {

                    val newInstance: AppExecutor? = INSTANCE
                    if (newInstance != null) {
                        return newInstance!!
                    }
                }
                if (INSTANCE == null) {
                    INSTANCE = AppExecutor(Executors.newFixedThreadPool(2, MyThreadFactory(MyExceptionHandler())),
                            Executors.newFixedThreadPool(3, MyThreadFactory(MyExceptionHandler())),
                            Executors.newFixedThreadPool(1, MyThreadFactory(MyExceptionHandler())),
                            MainThreadExecutor())
                }
                return INSTANCE!!
            }
    }

    fun executeOnNetworkThread(block: ()->Unit) {

        networkIO.execute {
            block()
        }
    }

    fun executeOnDatabaseThread(block: ()->Unit) {

        dbIO.execute {
            block()
        }
    }

   class MyThreadFactory: ThreadFactory{

       var handler: Thread.UncaughtExceptionHandler?=null
       constructor(handler: Thread.UncaughtExceptionHandler){
           this.handler = handler
       }

       val defaultFactory = Executors.defaultThreadFactory()

       override fun newThread(r: Runnable?): Thread {
           val thread = defaultFactory.newThread(r)
           thread.setUncaughtExceptionHandler(handler)
            return thread
       }

   }

    class MyExceptionHandler: Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {


            if(e != null){
                e?.printStackTrace()
                Log.e("EXCEPTION","Inside AppExecutor uncaughtException")
                Log.e("EXCEPTION",e?.message)
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t,e)
            }

        }

    }
}
