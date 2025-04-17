package com.purkt.mindexpense.core.data.expense.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id") val localId: Int,
    @ColumnInfo(name = "remote_id") val remoteId: String? = null,
    @ColumnInfo(name = "owner_user_id") val ownerUserId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "recipient") val recipient: String,
    @ColumnInfo(name = "note") val note: String? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "paid_at") val paidAt: LocalDateTime,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)