package com.example.a0112523013_fachrireyhan_p1.navigation

import kotlinx.serialization.Serializable

@Serializable data object StudentListRoute
@Serializable data class StudentDetailRoute(val studentId: Int)
@Serializable data class StudentFormRoute(val studentId: Int = 0)
@Serializable data object StudentSettingsRoute
