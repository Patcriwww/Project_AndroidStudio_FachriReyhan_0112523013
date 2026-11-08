package com.example.a0112523013_fachrireyhan_p1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.a0112523013_fachrireyhan_p1.ui.route.StudentDetailRouteContent
import com.example.a0112523013_fachrireyhan_p1.ui.route.StudentFormRouteContent
import com.example.a0112523013_fachrireyhan_p1.ui.route.StudentListRouteContent
import com.example.a0112523013_fachrireyhan_p1.ui.route.StudentSettingsRouteContent
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel

@Composable
fun StudentAppNavHost(
    navController: NavHostController,
    model: StudentViewModel,
    onFeedback: (String) -> Unit
) {
    NavHost(navController, startDestination = StudentListRoute) {
        composable<StudentListRoute> {
            StudentListRouteContent(
                model = model,
                onStudentClick = { id -> navController.navigate(StudentDetailRoute(id)) },
                onAddClick = { navController.navigate(StudentFormRoute()) },
                onSettingsClick = { navController.navigate(StudentSettingsRoute) }
            )
        }
        composable<StudentDetailRoute> { entry ->
            val studentId = entry.toRoute<StudentDetailRoute>().studentId
            StudentDetailRouteContent(
                model = model,
                studentId = studentId,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(StudentFormRoute(studentId)) },
                onDeleted = { message ->
                    navController.popBackStack()
                    onFeedback(message)
                },
                onMessage = onFeedback
            )
        }
        composable<StudentFormRoute> { entry ->
            val studentId = entry.toRoute<StudentFormRoute>().studentId
            StudentFormRouteContent(
                model = model,
                sessionKey = entry.id,
                editingId = studentId,
                onBack = { navController.popBackStack() },
                onSaved = { message ->
                    navController.popBackStack()
                    onFeedback(message)
                },
                onMessage = onFeedback
            )
        }
        composable<StudentSettingsRoute> {
            StudentSettingsRouteContent(
                model = model,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
