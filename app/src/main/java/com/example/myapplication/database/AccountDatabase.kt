package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by nampham on 5/17/21.
 */
@Database(entities = arrayOf(Account::class), version = 1, exportSchema = false)
public abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        @Volatile
        private var INSTANCE: AccountDatabase? = null

        fun getDatabase(ctx: Context): AccountDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    AccountDatabase::class.java, "account_database"
                ).build()
                INSTANCE  = instance
                instance
            }
        }
    }
}