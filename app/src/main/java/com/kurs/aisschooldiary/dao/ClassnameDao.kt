package com.kurs.aisschooldiary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kurs.aisschooldiary.models.Classname

@Dao
interface ClassnameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(classname: Classname)

    @Update
    suspend fun update(classname: Classname)

    @Delete
    suspend fun delete(classname: Classname)

    @Query("SELECT * FROM classname WHERE classnameId = :id")
    suspend fun getClassnameById(id: Long): Classname?

    @Query("SELECT * FROM classname")
    suspend fun getAllClassnames(): List<Classname>
}
