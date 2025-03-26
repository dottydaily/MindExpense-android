package com.purkt.mindexpense.data.expense.model

import java.time.LocalDateTime

/**
 * Model class for Expense information.
 *
 * @param localId The auto-generated id in local database. This can be empty in case that this expense is already synced online.
 * @param remoteId The auto-generated id in remote database. This can be empty in case that this expense is only created in offline.
 * @param ownerUserId The user id who owns this expense.
 * @param title The title of this expense.
 * @param receiver The receiver of this expense.
 * @param note The note of this expense.
 * @param amount The amount of this expense.
 * @param imageUrl The image url of this expense.
 * @param paidAt The [LocalDateTime] when this expense is paid.
 * @param createdAt The [LocalDateTime] when this expense is created.
 * @param updatedAt The [LocalDateTime] when this expense is updated.
 */
data class Expense(
    val localId: Int = DEFAULT_LOCAL_ID,
    val remoteId: Int? = null,
    val ownerUserId: Int,
    val title: String = "",
    val receiver: String = "",
    val note: String = "",
    val amount: Double = 0.0,
    val imageUrl: String = "",
    val paidAt: LocalDateTime = LocalDateTime.now(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun hasBeenSyncedAtLeastOnce(): Boolean {
        return remoteId != null
    }

    companion object {
        const val DEFAULT_LOCAL_ID = 0 // Room recognize this as a value to be ignored when inserting
    }
}
