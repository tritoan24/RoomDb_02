package toanntph32395.fpoly.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import toanntph32395.fpoly.roomdb.DAO.StudentDao
import toanntph32395.fpoly.roomdb.model.StudentModel

@Database(entities = arrayOf(StudentModel::class), version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}