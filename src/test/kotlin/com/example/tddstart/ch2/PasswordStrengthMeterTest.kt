package com.example.tddstart.ch2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * 길이가 8글자 이상
 * 0부터 9 사이의 숫자를 포함
 * 대문자 포함
 * 3개의 규칙을 모두 충족하면 "강함"
 * 2개의 규칙을 충족하면 "보통"
 * 1개 이하의 규칙을 충족하면 "약함"
 */
class PasswordStrengthMeterTest {
    @Test
    fun 세개의규칙을_모두_충족하면_STRONG() {
        val sut = PasswordStrengthMeter("A1234567")
        assertThat(sut.meter()).isEqualTo(PasswordStrength.STRONG)
    }

}

class PasswordStrengthMeter(
        private val password: String,
) {
    fun meter(): PasswordStrength {
        var checkCounter = 0;
        if (checkLetterSize()) checkCounter++
        if (checkNumber()) checkCounter++
        if (checkCapitalLetter()) checkCounter++

        if (checkCounter >= 3) return PasswordStrength.STRONG
        if (checkCounter == 2) return PasswordStrength.NORMAL
        return PasswordStrength.WEAK
    }

    private fun checkLetterSize() = password.length >= 8

    private fun checkNumber(): Boolean {
        val char = password.toCharArray()
        for (c in char) {
            if (c in '0'..'9') return true
        }
        return false
    }

    private fun checkCapitalLetter(): Boolean {
        val char = password.toCharArray()
        for (c in char) {
            if (c in 'A'..'Z') return true
        }
        return false
    }

}

enum class PasswordStrength {
    STRONG, NORMAL, WEAK
}