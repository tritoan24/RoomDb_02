package toanntph32395.fpoly.roomdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentModel (
    @PrimaryKey(autoGenerate = true) var stdid: Int = 0,
    @ColumnInfo(name = "ho ten") var hoten: String?,
    @ColumnInfo(name = "mssv") var mssv: String?,
    @ColumnInfo(name = "daratruong") var daratruong: Boolean?
)