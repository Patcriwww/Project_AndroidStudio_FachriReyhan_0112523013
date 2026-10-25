package com.example.a0112523013_fachrireyhan_p1

import com.example.a0112523013_fachrireyhan_p1.validation.*
import org.junit.Assert.*
import org.junit.Test

class StudentValidatorTest {
    @Test fun name_minimum_three_characters() {
        assertNotNull(validateName("AB"))
        assertNull(validateName("Fachri"))
    }
    @Test fun nim_must_be_ten_digits() {
        assertNotNull(validateNim("123abc"))
        assertNotNull(validateNim("1234"))
        assertNull(validateNim("0112523013"))
    }
    @Test fun email_format_required() {
        assertNotNull(validateEmail("salah"))
        assertNull(validateEmail("user@example.com"))
    }
    @Test fun semester_has_bounds() {
        assertNotNull(validateSemester(15))
        assertNull(validateSemester(7))
    }
    @Test fun duplicate_nim_is_rejected() {
        assertNotNull(validateUniqueNim("0112523013", listOf("0112523013")))
        assertNull(validateUniqueNim("0112523013", listOf("0112523003")))
    }
}
