package id.xxx.module

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class AndroidJUnitRunner : AndroidJUnitRunner() {

    class App : Application()

    override fun newApplication(
        cl: ClassLoader?, className: String?, context: Context?
    ): Application = super.newApplication(cl, App::class.java.name, context)
}