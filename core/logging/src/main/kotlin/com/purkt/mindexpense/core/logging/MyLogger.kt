package com.purkt.mindexpense.core.logging

interface MyLogger {
    fun i(tag: String? = null, message: String, vararg args: Any?)
    fun i(e: Throwable?, tag: String? = null)
    fun v(tag: String? = null, message: String, vararg args: Any?)
    fun v(e: Throwable?, tag: String? = null)
    fun d(tag: String? = null, message: String, vararg args: Any?)
    fun d(e: Throwable?, tag: String? = null)
    fun w(tag: String? = null, message: String, vararg args: Any?)
    fun w(e: Throwable?, tag: String? = null)
    fun e(tag: String? = null, message: String, vararg args: Any?)
    fun e(e: Throwable?, tag: String? = null)
}