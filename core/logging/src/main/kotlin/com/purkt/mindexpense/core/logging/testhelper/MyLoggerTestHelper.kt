package com.purkt.mindexpense.core.logging.testhelper

import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.core.logging.MyLogger
import io.mockk.every
import io.mockk.just
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.unmockkObject
import org.koin.core.module.Module
import org.koin.dsl.module

fun setUpMockAppLogger() {
    mockkObject(AppLogger)
    every { AppLogger.i(message = any()) } just runs
    every { AppLogger.d(message = any()) } just runs
    every { AppLogger.v(message = any()) } just runs
    every { AppLogger.w(message = any()) } just runs
    every { AppLogger.e(message = any()) } just runs
}

fun tearDownMockAppLogger() {
    unmockkObject(AppLogger)
}

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