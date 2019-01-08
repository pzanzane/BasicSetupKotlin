package com.clevertrap.basicsetupkotlin.utility

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by fidel25 on 12/04/2017.
 */

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
                    INSTANCE = AppExecutor(Executors.newFixedThreadPool(2),
                            Executors.newFixedThreadPool(3),
                            Executors.newFixedThreadPool(1),
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
}
