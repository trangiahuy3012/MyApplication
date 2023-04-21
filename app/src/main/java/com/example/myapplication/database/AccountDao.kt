package com.example.myapplication.database

import androidx.room.*


@Dao
interface AccountDao {
    @Query("SELECT * FROM account ORDER BY username ASC")
    suspend fun getAccounts(): List<Account>

    @Query(
        "SELECT * FROM account WHERE email = :email AND password = " +
                ":pass"
    )
    suspend fun searchAccount(email: String,  pass: String): Account?

    @Query(
        "SELECT * FROM account WHERE username = :user"
    )
    suspend fun checkUsernameExisting(user: String): Account?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)

    @Query("DELETE FROM account")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(account: Account)

    @Update
    suspend fun update(account: Account)
}