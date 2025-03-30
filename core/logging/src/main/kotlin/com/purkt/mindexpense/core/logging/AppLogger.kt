package com.purkt.mindexpense.core.logging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppLogger: MyLogger, KoinComponent {
    private val _myLogger by inject<MyLogger>()

    override fun i(tag: String?, message: String, vararg args: Any?) {
        _myLogger.i(tag = tag, message = message, args = args)
    }

    override fun i(e: Throwable?, tag: String?) {
        _myLogger.i(tag = tag, e = e)
    }

    override fun v(tag: String?, message: String, vararg args: Any?) {
        _myLogger.v(tag = tag, message = message, args = args)
    }

    override fun v(e: Throwable?, tag: String?) {
        _myLogger.v(tag = tag, e = e)
    }

    override fun d(tag: String?, message: String, vararg args: Any?) {
        _myLogger.d(tag = tag, message = message, args = args)
    }

    override fun d(e: Throwable?, tag: String?) {
        _myLogger.d(tag = tag, e = e)
    }

    override fun w(tag: String?, message: String, vararg args: Any?) {
        _myLogger.w(tag = tag, message = message, args = args)
    }

    override fun w(e: Throwable?, tag: String?) {
        _myLogger.w(tag = tag, e = e)
    }

    override fun e(tag: String?, message: String, vararg args: Any?) {
        _myLogger.e(tag = tag, message = message, args = args)
    }

    override fun e(e: Throwable?, tag: String?) {
        _myLogger.e(tag = tag, e = e)
    }

    fun i(message: String, vararg args: Any?) = i(tag = getCallerMethodName(), message = message, args = args)
    fun v(message: String, vararg args: Any?) = v(tag = getCallerMethodName(), message = message, args = args)
    fun d(message: String, vararg args: Any?) = d(tag = getCallerMethodName(), message = message, args = args)
    fun w(message: String, vararg args: Any?) = w(tag = getCallerMethodName(), message = message, args = args)
    fun e(message: String, vararg args: Any?) = e(tag = getCallerMethodName(), message = message, args = args)

    fun i(e: Throwable) = i(tag = getCallerMethodName(), e = e)
    fun v(e: Throwable) = v(tag = getCallerMethodName(), e = e)
    fun d(e: Throwable) = d(tag = getCallerMethodName(), e = e)
    fun w(e: Throwable) = w(tag = getCallerMethodName(), e = e)
    fun e(e: Throwable) = e(tag = getCallerMethodName(), e = e)

    private fun getCallerMethodName(): String {
        return try {
            val topCallerStackTrace = Throwable().stackTrace[2]
            val className = topCallerStackTrace.className.substringAfterLast(".")
            val line = topCallerStackTrace.lineNumber

            return "$className[L:$line]"
        } catch (_: Throwable) {
            ""
        }
    }
}
