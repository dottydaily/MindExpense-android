package com.purkt.mindexpense.core.data.expense.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.purkt.mindexpense.core.data.common.DATE_FULL_PATTERN
import com.purkt.mindexpense.core.data.common.TIME_12_HOUR_FORMAT_PATTERN
import com.purkt.mindexpense.core.data.common.toDateTimeStringOrNull
import java.time.LocalDateTime

/**
 * Model class for Expense information.
 *
 * @param localId The auto-generated id in local database. This can be empty in case that this expense is already synced online.
 * @param remoteId The auto-generated id in remote database. This can be empty in case that this expense is only created in offline.
 * @param ownerUserId The user id who owns this expense.
 * @param title The title of this expense.
 * @param recipient The receiver of this expense.
 * @param note The note of this expense.
 * @param amount The amount of this expense.
 * @param imageUrl The image url of this expense.
 * @param paidAt The [LocalDateTime] when this expense is paid.
 * @param createdAt The [LocalDateTime] when this expense is created.
 * @param updatedAt The [LocalDateTime] when this expense is updated.
 */
@Immutable
data class Expense(
    val localId: Int = DEFAULT_LOCAL_ID,
    val remoteId: Int? = null,
    val ownerUserId: Int,
    val title: String = "",
    val recipient: String = "",
    val note: String = "",
    val amount: Double = 0.0,
    val imageUrl: String = "",
    val paidAt: LocalDateTime = LocalDateTime.now(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    val uniqueId: String = if (remoteId != null) "R-$remoteId" else "L-$localId"
    val currentId: Int = remoteId ?: localId

    /**
     * Check if this expense has been synced online or not.
     */
    fun hasBeenSyncedAtLeastOnce(): Boolean {
        return remoteId != null
    }

    /**
     * Get [paidAt] as a full date string. Ex."Wednesday, 2 April 2025".
     */
    fun getPaidAtFullDateStringOrNull(): String? {
        return paidAt.toDateTimeStringOrNull(DATE_FULL_PATTERN)
    }

    /**
     * Get [paidAt] as a time string. Ex."12:00 PM".
     */
    fun getPaidAtTimeStringOrNull(): String? {
        return paidAt.toDateTimeStringOrNull(TIME_12_HOUR_FORMAT_PATTERN)
    }

    companion object {
        const val DEFAULT_LOCAL_ID = 0 // Room recognize this as a value to be ignored when inserting
    }
}
