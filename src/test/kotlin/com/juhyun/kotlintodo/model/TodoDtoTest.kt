package com.juhyun.kotlintodo.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator

class TodoDtoTest {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest() {
        // given
        val todoDto = TodoDto().apply {
            this.title = "테스트"
            this.description = ""
            this.schedule = "2020-10-20 13:00:00"
        }

        // when
        val result = validator.validate(todoDto)

        // then
        assertThat(result.isEmpty()).isEqualTo(true)
    }

    @Test
    fun title은_제목이_지정되어야_한다() {
        // given
        val todoDto = TodoDto().apply {
            this.title = ""
            this.description = ""
            this.schedule = "2020-10-20 13:00:00"
        }

        // when
        val result = validator.validate(todoDto)

        // then
        result.forEach {
            assertThat(it.messageTemplate).contains("NotBlank")
            assertThat(it.message).contains("공백")
        }

    }

}