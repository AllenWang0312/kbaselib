package edu.tjrac.swant.baselib.util

import android.text.TextUtils
import android.util.Log

import java.io.DataOutputStream
import java.io.IOException

/**
 * Created by wpc on 2018-12-07.
 */

class ShellCommandExecutor {

    private val mCommands: StringBuilder

    init {
        mCommands = StringBuilder()
    }

    fun execute(): Int {
        return execute(mCommands.toString())
    }

    fun addCommand(cmd: String): ShellCommandExecutor {
        if (TextUtils.isEmpty(cmd)) {
            throw IllegalArgumentException("command can not be null.")
        }
        mCommands.append(cmd)
        mCommands.append("\n")
        return this
    }

    companion object {


        private val TAG = "ShellCommandExecutor"

        private fun execute(command: String): Int {
            var result = -1
            var dos: DataOutputStream? = null
            try {
                val p = Runtime.getRuntime().exec("su")
                dos = DataOutputStream(p.outputStream)
                Log.i(TAG, command)
                dos.writeBytes(command + "\n")
                dos.flush()
                dos.writeBytes("exit\n")
                dos.flush()
                p.waitFor()
                result = p.exitValue()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (dos != null) {
                    try {
                        dos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return result
        }
    }
}
