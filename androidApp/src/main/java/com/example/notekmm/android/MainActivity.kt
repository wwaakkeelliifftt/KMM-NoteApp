package com.example.notekmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notekmm.android.note_detail.NOTE_DETAIL_SCREEN
import com.example.notekmm.android.note_detail.NoteDetailScreen
import com.example.notekmm.android.note_list.NOTE_LIST_SCREEN
import com.example.notekmm.android.note_list.NoteListScreen
import com.example.notekmm.android.util.SSH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NOTE_LIST_SCREEN
                ) {
                    composable(route = NOTE_LIST_SCREEN) {
                        NoteListScreen(navController)
                    }
                    composable(
                        route = "$NOTE_DETAIL_SCREEN/{${SSH.EXISTING_NOTE_ID}}",
                        arguments = listOf(
                            navArgument(name = SSH.EXISTING_NOTE_ID) {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) { navBackStackEntry ->
                        val noteId = navBackStackEntry.arguments?.getLong(SSH.EXISTING_NOTE_ID) ?: -1
                        NoteDetailScreen(noteId = noteId, navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text, color = Color.Cyan)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
