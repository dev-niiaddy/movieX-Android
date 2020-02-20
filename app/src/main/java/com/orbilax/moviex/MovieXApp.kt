package com.orbilax.moviex

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MovieXApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .name("movieX.realm")
            .schemaVersion(3)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}