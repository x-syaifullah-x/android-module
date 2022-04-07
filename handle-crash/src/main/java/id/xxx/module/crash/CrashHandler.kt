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

    private val uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    private val receiverErrors by lazy { HashSet<AbstractReceiveError>() }

    fun <T : AbstractReceiveError> register(receiverError: T) =
        receiverErrors.add(receiverError)

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        try {
            throwable.printStackTrace(pw)
            receiverErrors.forEach { re ->
                re.onError("$sw", throwable)
            }
        } finally {
            pw.close()
            sw.close()
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable)
            } else {
                exitProcess(1)
            }
        }
    }
}