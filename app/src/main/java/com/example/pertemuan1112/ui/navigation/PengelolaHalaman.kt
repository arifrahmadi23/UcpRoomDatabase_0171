package com.example.pertemuan1112.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan1112.ui.view.DestinasiHome
import com.example.pertemuan1112.ui.view.HomeScreen
import com.example.pertemuan1112.ui.view.dosen.DestinasiInsertDosen
import com.example.pertemuan1112.ui.view.dosen.HomeDsnView
import com.example.pertemuan1112.ui.view.dosen.InsertDsnView
import com.example.pertemuan1112.ui.view.matakuliah.DestinasiInsertMk
import com.example.pertemuan1112.ui.view.matakuliah.DetailMkView
import com.example.pertemuan1112.ui.view.matakuliah.HomeMkView
import com.example.pertemuan1112.ui.view.matakuliah.InsertMkView
import com.example.pertemuan1112.ui.view.matakuliah.UpdateMkView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController, startDestination = DestinasiHome.route){
        composable(route = DestinasiHome.route){
            HomeScreen(
                onClickDsn = {navController.navigate(DestinasiHomeDosen.route)},
                onClickMk = {navController.navigate(DestinasiHomeMataKuliah.route)}
            )
        }
        composable(
            route = DestinasiHomeDosen.route
        ){
            HomeDsnView(
                onAddDsn = {navController.navigate(DestinasiInsertDosen.route)},
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertDosen.route
        ){
            InsertDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            route = DestinasiHomeMataKuliah.route
        ){
            HomeMkView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMk.route}/$kode")
                    println("PengelolaHalaman: kode = $kode ")
                },
                onAddMk = {
                    navController.navigate(DestinasiInsertMk.route)
                },
                onBack = {navController.popBackStack()},
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertMk.route
        ){
            InsertMkView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            DestinasiDetailMk.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMk.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMk.KODE)
            kode?.let { kode ->
                DetailMkView (
                    onBack = { navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMk.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdateMk.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMk.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            UpdateMkView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

    }


}
