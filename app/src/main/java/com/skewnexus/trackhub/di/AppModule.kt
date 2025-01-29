package com.skewnexus.trackhub.di

import androidx.navigation.NavHostController
import com.skewnexus.trackhub.MainActivity
import org.koin.dsl.module

val appModule = module {
    scope<MainActivity> {
        scoped { (navController: NavHostController) -> navController }
    }
}