package toanntph32395.fpoly.roomdb.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import toanntph32395.fpoly.roomdb.model.StudentModel
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {
    @Query("SELECT * FROM studentmodel")
    fun getAll(): Flow<List<StudentModel>>

    @Query("SELECT * FROM studentmodel WHERE stdid IN (:studentIds)")
    fun loadAllByIds(studentIds: IntArray):Flow<StudentModel>

    @Insert
    suspend fun insertStudent(vararg students: StudentModel)

    @Delete
    suspend fun delete(student: StudentModel)

    @Update
    suspend fun updateStudent(student: StudentModel)
}