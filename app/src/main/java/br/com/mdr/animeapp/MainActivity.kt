package br.com.mdr.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.mdr.animeapp.navigation.SetupNavGraph
import br.com.mdr.animeapp.ui.theme.AnimeAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeAppTheme {
                //Define default activity navigation controller
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}