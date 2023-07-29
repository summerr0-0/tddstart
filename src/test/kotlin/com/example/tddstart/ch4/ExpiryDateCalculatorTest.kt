package com.example.tddstart.ch4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * 만료일을 계산하는 기능
 * 매달 1만원을 선불로 납부한다
 * 납부일 기준으로 한달 뒤가 서비스 만료일이 된다
 * 2개월 이상 요금을 납부할 수 있다
 * 10만원을 납부하면 서비스를 1년 제공한다
 */
class ExpiryDateCalculatorTest {

    //낸 날 낸 금액
    @Test
    fun 만원_납부하면_한달_뒤가_만료일이다() {
        val sut = ExpiryDateCalculator()
        val result = sut.calculate(20000L, LocalDate.of(2023, 7, 29))
        assertThat(result).isEqualTo(LocalDate.of(2023, 9, 29))
    }

    @Test
    fun 납부는_여러개월_납부가_가능하다() {
        val sut = ExpiryDateCalculator()
        val result = sut.calculate(10000L, LocalDate.of(2023, 7, 29))
        assertThat(result).isEqualTo(LocalDate.of(2023, 8, 29))
    }

    @Test
    fun 만원미만_납부는_안된다() {
        val sut = ExpiryDateCalculator()

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            sut.calculate(10L, LocalDate.of(2023, 7, 29))
        }
    }

    @Test
    fun 십만원은_1년이다() {
        val sut = ExpiryDateCalculator()

        val result = sut.calculate(100000L, LocalDate.of(2023, 7, 29))
        assertThat(result).isEqualTo(LocalDate.of(2024, 7, 29))
    }

    @Test
    fun 십이만원은_1년2개월이다() {
        val sut = ExpiryDateCalculator()

        val result = sut.calculate(120000L, LocalDate.of(2023, 7, 29))
        assertThat(result).isEqualTo(LocalDate.of(2024, 9, 29))
    }
}

class ExpiryDateCalculator {
    fun calculate(amount: Long, payDate: LocalDate): LocalDate {
        if(amount%10000L!=0L) throw IllegalArgumentException("만원단위만 결제 가능")

        var money = amount;
        var date = 0L;
        while (money >= 100000L){
            money -=100000L;
            date+=12;
        }
        date += money/10000L

        return payDate.plusMonths(date)
    }
}