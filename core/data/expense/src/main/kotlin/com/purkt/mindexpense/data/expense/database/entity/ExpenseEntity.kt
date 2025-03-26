package com.purkt.mindexpense.data.expense.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id") val localId: Int,
    @ColumnInfo(name = "remote_id") val remoteId: String? = null,
    @ColumnInfo(name = "owner_user_id") val ownerUserId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "receiver") val receiver: String,
    @ColumnInfo(name = "note") val note: String? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "paid_at") val paidAtIsoDateTime: String,
    @ColumnInfo(name = "created_at") val createdAtIsoDateTime: String,
    @ColumnInfo(name = "updated_at") val updatedAtIsoDateTime: String,
)