package com.purkt.mindexpense

import android.app.Application
import com.purkt.mindexpense.di.mindExpenseAppModules
import org.koin.core.context.startKoin

class MindExpenseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mindExpenseAppModules)
        }
    }
}