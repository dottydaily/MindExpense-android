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
 */
data class User(
    val localId: String = "",
    val remoteId: String = "",
    val email: String = "",
    val displayName: String = "",
    val profileUrl: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
