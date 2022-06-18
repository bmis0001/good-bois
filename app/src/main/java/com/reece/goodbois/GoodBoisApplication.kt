package com.reece.goodbois

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Generates all the necessary dagger code for dependency injection
@HiltAndroidApp
class GoodBoisApplication : Application()