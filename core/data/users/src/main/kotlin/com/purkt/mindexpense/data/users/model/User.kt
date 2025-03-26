package com.purkt.mindexpense.data.users.model

import java.time.LocalDateTime

/**
 * Model class for user information.
 *
 * @param localId The auto-generated id in local database. This can be empty in case that this user is already synced online.
 * @param remoteId The auto-generated id in remote database. This can be empty in case that this user is only created in offline.
 * @param email The email of this user.
 * @param displayName The display name of this user.
 * @param profileUrl The profile url of this user.
 * @param createdAt The [LocalDateTime] when this user is created.
 * @param updatedAt The [LocalDateTime] when this user is updated.
 * @param isUsing Whether this user is currently using the app.
 */
data class User(
    val localId: Int = DEFAULT_LOCAL_ID,
    val remoteId: Int? = null,
    val email: String = "",
    val displayName: String = "",
    val profileUrl: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isUsing: Boolean = false,
) {
    fun isOnline(): Boolean = remoteId != null

    companion object {
        const val DEFAULT_LOCAL_ID = 0 // Room recognize this as a value to be ignored when inserting
    }
}
