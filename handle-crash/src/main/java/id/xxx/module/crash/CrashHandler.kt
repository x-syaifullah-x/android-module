package id.xxx.module.crash

import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    companion object {

        @Volatile
        private var INSTANCE: CrashHandler? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: CrashHandler().also {
                INSTANCE = it
                Thread.setDefaultUncaughtExceptionHandler(INSTANCE)
            }
        }
    }

    private val tUEH = Thread.getDefaultUncaughtExceptionHandler()

    private val ares by lazy { ArrayList<AbstractReceiveError>() }

    fun <ARE : AbstractReceiveError> register(are: ARE) = ares.add(are)

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        try {
            throwable.printStackTrace(pw)
            ares.forEach { re ->
                re.onError("$sw", throwable)
            }
        } finally {
            pw.close()
            sw.close()
            if (tUEH != null) {
                tUEH.uncaughtException(thread, throwable)
            } else {
                exitProcess(1)
            }
        }
    }
}