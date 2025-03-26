package com.purkt.mindexpense.data.users.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.purkt.mindexpense.data.users.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query(
        """
            SELECT * FROM users WHERE
                CASE WHEN remote_id IS NULL THEN local_id = :userId 
                ELSE users.remote_id = :userId 
                END
        """)
    fun getUserById(userId: Int): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE is_using = true")
    fun getCurrentUser(): Flow<UserEntity?>

    @Insert
    fun createUser(user: UserEntity): Long

    @Update
    fun updateUser(user: UserEntity): Int

    @Delete
    fun deleteUser(user: UserEntity): Int
}