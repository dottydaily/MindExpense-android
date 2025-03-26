package com.purkt.mindexpense.data.users.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id") val localId: Int = 0,
    @ColumnInfo(name = "remote_id") val remoteId: Int? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "display_name") val displayName: String? = null,
    @ColumnInfo(name = "profile_url") val profileUrl: String? = null,
    @ColumnInfo(name = "created_at") val createdAtIsoDateTime: String,
    @ColumnInfo(name = "updated_at") val updatedAtIsoDateTime: String,
    @ColumnInfo(name = "is_using") val isUsing: Boolean = false,
)
