package com.purkt.mindexpense

import android.app.Application
import com.purkt.mindexpense.di.mindExpenseAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MindExpenseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mindExpenseAppModules)
            androidContext(this@MindExpenseApplication)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO(Implement tree for release mode such as Crashlytics)
        }
    }
}