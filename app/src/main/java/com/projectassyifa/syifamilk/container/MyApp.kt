package com.projectassyifa.syifamilk.container

import android.app.Application

class MyApp : Application() {
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}