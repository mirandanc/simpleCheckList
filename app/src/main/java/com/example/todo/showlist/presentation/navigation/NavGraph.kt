package com.example.todo.showlist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.showlist.presentation.add_checklist.AddChecklistScreen
import com.example.todo.showlist.presentation.edit_list_screen.EditChecklistScreen
import com.example.todo.showlist.presentation.lists_screen.CheckListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.CheckListsScreen.route) {
        composable(route = Screen.CheckListsScreen.route) { navBackStackEntry ->
            CheckListScreen(
                onAddCheckList = { navController.navigate(Screen.AddChecklistScreen.route) },
                onEditCheckList = { listid ->
                    navController.navigate(Screen.EditChecklistScreen.withId(listid))
                },
                navBackStackEntry = navBackStackEntry
            )
        }
        composable(route = Screen.AddChecklistScreen.route) {
            AddChecklistScreen(navController = navController)
        }
        composable(
            route = Screen.EditChecklistScreen.route,
            arguments = listOf(navArgument("listId") { type = NavType.StringType })
        ) { backStackEntry ->
            EditChecklistScreen(
                navController = navController,
                listId = backStackEntry.arguments?.getString("listId")
            )
        }
    }
}

sealed class Screen(val route: String) {
    object CheckListsScreen : Screen("check_lists_screen")
    object AddChecklistScreen : Screen("add_checklist_screen")
    object EditChecklistScreen : Screen("edit_checklist_screen/{listId}") {
        fun withId(id: String): String {
            return "edit_checklist_screen/$id"
        }
    }
}