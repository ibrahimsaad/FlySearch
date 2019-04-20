package io.github.mohamedisoliman.flysearch

import android.app.Application
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class FlySearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
      Timber.plant(Timber.DebugTree())
      startKoin(this, listOf(dataModule, domainModule, presentationModule))
    }
}