package toanntph32395.fpoly.roomdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import toanntph32395.fpoly.roomdb.HomeScreen
import toanntph32395.fpoly.roomdb.screen.AddStudentScreen
import toanntph32395.fpoly.roomdb.screen.UpdateStudent

@Composable
fun nav_main(navControlle: NavHostController){

    NavHost(navController = navControlle, startDestination = "home") {
        composable("home") { HomeScreen(navController = navControlle)}
        composable("addstudent") { AddStudentScreen(navController = navControlle)}
        composable("updatestudent/{stdid}",
            arguments = listOf(navArgument("stdid") {type = NavType.IntType})
        ) { backStackEntry ->  
            val stdid = backStackEntry.arguments?.getInt("stdid") ?: 0
            UpdateStudent(navController = navControlle, stdid = stdid)
        }
    }
}