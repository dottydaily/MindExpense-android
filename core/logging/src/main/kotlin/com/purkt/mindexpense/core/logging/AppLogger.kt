package com.purkt.mindexpense.core.logging

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppLogger: MyLogger, KoinComponent {
    private val _myLogger by inject<MyLogger>()

    override fun i(tag: String?, message: String, vararg args: Any?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.i(tag = targetTag, message = message, args = args)
    }

    override fun i(e: Throwable?, tag: String?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.i(e, targetTag)
    }

    override fun v(tag: String?, message: String, vararg args: Any?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.v(tag = targetTag, message = message, args = args)
    }

    override fun v(e: Throwable?, tag: String?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.v(e, targetTag)
    }

    override fun d(tag: String?, message: String, vararg args: Any?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.d(tag = targetTag, message = message, args = args)
    }

    override fun d(e: Throwable?, tag: String?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.d(e, targetTag)
    }

    override fun w(tag: String?, message: String, vararg args: Any?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.w(tag = targetTag, message = message, args = args)
    }

    override fun w(e: Throwable?, tag: String?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.w(e, targetTag)
    }

    override fun e(tag: String?, message: String, vararg args: Any?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.e(tag = targetTag, message = message, args = args)
    }

    override fun e(e: Throwable?, tag: String?) {
        val targetTag = tag ?: getCallerMethodName()
        _myLogger.e(e, targetTag)
    }

    fun i(message: String, vararg args: Any?) {
        _myLogger.i(tag = getCallerMethodName(), message = message, args = args)
    }
    fun v(message: String, vararg args: Any?) {
        _myLogger.v(tag = getCallerMethodName(), message = message, args = args)
    }
    fun d(message: String, vararg args: Any?) {
        _myLogger.d(tag = getCallerMethodName(), message = message, args = args)
    }
    fun w(message: String, vararg args: Any?) {
        _myLogger.w(tag = getCallerMethodName(), message = message, args = args)
    }
    fun e(message: String, vararg args: Any?) {
        _myLogger.e(tag = getCallerMethodName(), message = message, args = args)
    }

    private fun getCallerMethodName(): String {
        val topCallerStackTrace = Throwable().stackTrace.getOrNull(2) ?: return ""
        val className = topCallerStackTrace.className.substringAfterLast(".")
        val line = topCallerStackTrace.lineNumber

        return "$className[L:$line]"
    }
}
