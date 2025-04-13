package com.purkt.mindexpense.core.logging

import org.koin.core.module.Module
import org.koin.dsl.module

fun Module.includedMockMyLogger() {
    includes(mockMyLoggerModule)
}

private val mockMyLoggerModule = module {
    single<MyLogger> { MockMyLogger() }
}

private class MockMyLogger: MyLogger {
    override fun i(tag: String?, message: String, vararg args: Any?) {}
    override fun i(e: Throwable?, tag: String?) {}
    override fun v(tag: String?, message: String, vararg args: Any?) {}
    override fun v(e: Throwable?, tag: String?) {}
    override fun d(tag: String?, message: String, vararg args: Any?) {}
    override fun d(e: Throwable?, tag: String?) {}
    override fun w(tag: String?, message: String, vararg args: Any?) {}
    override fun w(e: Throwable?, tag: String?) {}
    override fun e(tag: String?, message: String, vararg args: Any?) {}
    override fun e(e: Throwable?, tag: String?) {}
}