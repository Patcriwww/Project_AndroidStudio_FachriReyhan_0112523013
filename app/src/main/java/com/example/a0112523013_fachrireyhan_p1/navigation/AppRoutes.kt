package com.example.a0112523013_fachrireyhan_p1.navigation

import kotlinx.serialization.Serializable

/** Navigation 2.8+ type-safe routes; argumen studentId berupa Int. */
@Serializable data object StudentListRoute
@Serializable data class StudentDetailRoute(val studentId: Int)
/** studentId = 0 hanya untuk mode tambah, sedangkan ID existing > 0. */
@Serializable data class StudentFormRoute(val studentId: Int = 0)
