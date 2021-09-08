package com.juhyun.kotlintodo.repository

import com.juhyun.kotlintodo.datasource.Todo
import com.juhyun.kotlintodo.datasource.TodoDataBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TodoRepositoryImpl : TodoRepository {

    @Autowired
    lateinit var todoDataBase: TodoDataBase

    override fun save(todo: Todo): Todo? {

        // index가 존재할 경우 -> update
        return todo.index?.let { index ->
            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }
        } ?: kotlin.run {
            todo.apply {
                this.index = ++todoDataBase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
            }.run {
                todoDataBase.todoList.add(todo)
                this
            }
        }

    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach {
                save(it)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun delete(index: Int): Boolean {
        findOne(index)?.let {
            todoDataBase.todoList.remove(it)
            return true
        } ?: kotlin.run {
            return false
        }
    }

    override fun findOne(index: Int): Todo? {
        return todoDataBase.todoList.first {
            it.index == index
        }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDataBase.todoList
    }
}