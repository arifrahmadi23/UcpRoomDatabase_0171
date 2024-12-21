package com.example.pertemuan1112.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan1112.ui.view.dosen.DestinasiInsertDosen
import com.example.pertemuan1112.ui.view.dosen.HomeDsnView
import com.example.pertemuan1112.ui.view.dosen.InsertDsnView
import com.example.pertemuan1112.ui.view.matakuliah.InsertMkView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController, startDestination = DestinasiHomeDosen.route){
        composable(route = DestinasiHomeDosen.route){
            HomeDsnView(
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiInsertDosen.route
        ) {
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
    }


}
