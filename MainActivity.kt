package toanntph32395.fpoly.roomdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import toanntph32395.fpoly.roomdb.database.StudentDatabase
import toanntph32395.fpoly.roomdb.model.StudentModel
import toanntph32395.fpoly.roomdb.navigation.nav_main
import toanntph32395.fpoly.roomdb.ui.theme.RoomDbTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDbTheme {
                val navController = rememberNavController()
                Scaffold( modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(16.dp) ) { innerPadding ->
                    
                    nav_main(navControlle = navController)
                }
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavHostController) {

    val content = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val db = Room.databaseBuilder(
        context = content,
        StudentDatabase::class.java, "students-db2"
    ).build()

    var students by remember {
        mutableStateOf(mutableListOf<StudentModel>())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var studentToDelete by remember {
        mutableStateOf<StudentModel?>(null)
    }

    LaunchedEffect(Unit) {
        db.studentDao().getAll().collect {clientEntities ->
            students = clientEntities.toMutableList()
        }
    }

//    Dialog Delete
    if (showDialog && studentToDelete != null) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text(text = "Xac nhan xoa")},
            text = { Text(text = "Ban co chac chan muon xoa khong?")},
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch {
                        db.studentDao().delete(studentToDelete!!)
                        students = students.filter { it.stdid != studentToDelete!!.stdid }.toMutableList()
                        showDialog = false
                        studentToDelete = null
                    }
                }) {
                    Text(text = "Xac nhan")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    studentToDelete = null
                }) {
                    Text(text = "Huy")
                }
            }
        )
    }
    
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Quản lý sinh vien",
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = { 
            navController.navigate("addstudent")
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.White
            )
        ) {
            Text(text = "Add student")
        }

        LazyColumn {
            items(students) {student ->
                Box (
//                    modifier = Modifier.padding(10.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column (
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                        ) {
                            Text(text ="id: ${student.stdid}")
                            Text(text ="Ho ten: ${student.hoten}")
                            Text(text ="mssv: ${student.mssv}")
                            Text(text ="Trang thai: ${ if (student.daratruong == false) "Chua ra truong" else "Da ra truong"}" )
                        }

                        Column (
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            IconButton(onClick = {
                                studentToDelete = student
                                showDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Icon Delete",
                                    tint = Color.Red,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            IconButton(onClick = {
                                navController.navigate("updatestudent/${student.stdid}")
                            }) {
                                Icon(imageVector = Icons.Default.Edit,
                                    contentDescription = "Icon Edit",
                                    tint = Color.Blue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}