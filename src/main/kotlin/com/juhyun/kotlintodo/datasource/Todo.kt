package com.juhyun.kotlintodo.datasource

import com.juhyun.kotlintodo.model.TodoDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Todo(
        var index: Int? = null,

        var title: String? = null,

        var description: String? = null,

        var schedule: LocalDateTime? = null,

        var createdAt: LocalDateTime? = null,

        var updatedAt: LocalDateTime? = null
)

fun Todo.toEntity(todoDto: TodoDto): Todo {
    return Todo().apply {
        this.index = todoDto.index
        this.title = todoDto.title
        this.description =  todoDto.description
        this.schedule = LocalDateTime.parse(todoDto.schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todoDto.createdAt
        this.updatedAt = todoDto.updatedAt
    }
}
