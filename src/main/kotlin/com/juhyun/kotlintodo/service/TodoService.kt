package com.juhyun.kotlintodo.service

import com.juhyun.kotlintodo.datasource.Todo
import com.juhyun.kotlintodo.datasource.toEntity
import com.juhyun.kotlintodo.model.TodoDto
import com.juhyun.kotlintodo.repository.TodoRepositoryImpl
import org.springframework.stereotype.Service

@Service
class TodoService(val todoRepositoryImpl: TodoRepositoryImpl) {

    // Create
    fun create(todoDto: TodoDto): Todo? {
        return todoDto.let {
            Todo().toEntity(it)
        }.let {
            todoRepositoryImpl.save(it)
        }
    }

    // Read
    fun read(index: Int): Todo? {
        return todoRepositoryImpl.findOne(index)
    }

    fun readAll(): MutableList<Todo> {
        return todoRepositoryImpl.findAll()
    }

    // Update
    fun update(todoDto: TodoDto): Todo? {
        return todoDto.let {
            Todo().toEntity(it)
        }.let {
            todoRepositoryImpl.save(it)
        }
    }

    // Delete
    fun delete(index: Int): Boolean {
        return todoRepositoryImpl.delete(index)
    }

}