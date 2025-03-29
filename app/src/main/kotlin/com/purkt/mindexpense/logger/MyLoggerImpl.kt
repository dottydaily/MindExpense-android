package com.purkt.mindexpense.logger

import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.core.logging.MyLogger
import timber.log.Timber

/**
 * Implementation of [MyLogger] that uses [Timber] for logging.
 * This will be injected into [AppLogger] implicitly via Koin.
 * Every module who wants to use [MyLogger] should do log via [AppLogger] directly.
 */
internal class MyLoggerImpl: MyLogger {
    override fun i(tag: String?, message: String, vararg args: Any?) {
        Timber.tag(tag.orEmpty()).i(message, args)
    }

    override fun i(e: Throwable?, tag: String?) {
        Timber.tag(tag.orEmpty()).i(e)
    }

    override fun v(tag: String?, message: String, vararg args: Any?) {
        Timber.tag(tag.orEmpty()).v(message, args)
    }

    override fun v(e: Throwable?, tag: String?) {
        Timber.tag(tag.orEmpty()).v(e)
    }

    override fun d(tag: String?, message: String, vararg args: Any?) {
        Timber.tag(tag.orEmpty()).d(message, args)
    }

    override fun d(e: Throwable?, tag: String?) {
        Timber.tag(tag.orEmpty()).d(e)
    }

    override fun w(tag: String?, message: String, vararg args: Any?) {
        Timber.tag(tag.orEmpty()).w(message, args)
    }

    override fun w(e: Throwable?, tag: String?) {
        Timber.tag(tag.orEmpty()).w(e)
    }

    override fun e(tag: String?, message: String, vararg args: Any?) {
        Timber.tag(tag.orEmpty()).e(message, args)
    }

    override fun e(e: Throwable?, tag: String?) {
        Timber.tag(tag.orEmpty()).e(e)
    }
}