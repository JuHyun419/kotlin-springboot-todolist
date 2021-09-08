package com.juhyun.kotlintodo.model

import com.juhyun.kotlintodo.datasource.Todo
import io.swagger.annotations.ApiModelProperty
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotBlank

data class TodoDto(

        @field:ApiModelProperty(
                value = "DB Index",
                example = "1",
                required = false
        )
        var index: Int? = null,

        @field:ApiModelProperty(
                value = "일정제목",
                example = "일정관리",
                required = true
        )
        @field:NotBlank
        var title: String? = null,

        @field:ApiModelProperty(
                value = "일정설명",
                example = "13시 스타벅스",
                required = false
        )
        var description: String? = null,

        // yyyy-MM-dd HH:mm:ss
        @field:ApiModelProperty(
                value = "시간",
                example = "2021-09-08T11:23:42",
                required = true
        )
        @field:NotBlank
        var schedule: String? = null,

        @field:ApiModelProperty(
                value = "생성일자",
                example = "2021-09-08T11:23:42",
                required = false
        )
        var createdAt: LocalDateTime? = null,

        @field:ApiModelProperty(
                value = "수정일자",
                example = "2021-09-08T11:23:42",
                required = false
        )
        var updatedAt: LocalDateTime? = null
) {
    // TODO: 이전 커스텀 어노테이션 사용
    @AssertTrue(message = "yyyy-MM-dd HH:mm:ss 포맷에 맞지 않습니다.")
    fun isValidSchedule(): Boolean {
        return try {
            LocalDateTime.parse(schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            true
        } catch (e: Exception) {
            false
        }
    }
}

fun Todo.toDto(todo: Todo): TodoDto {
    return TodoDto().apply {
        this.index = todo.index
        this.title = todo.title
        this.description = todo.description
        this.schedule = todo.schedule?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todo.createdAt
        this.updatedAt = todo.updatedAt
    }
}