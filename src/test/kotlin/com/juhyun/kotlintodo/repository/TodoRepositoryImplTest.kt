package com.juhyun.kotlintodo.repository

import com.juhyun.kotlintodo.config.AppConfig
import com.juhyun.kotlintodo.datasource.Todo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
internal class TodoRepositoryImplTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach
    fun before() {
        todoRepositoryImpl.todoDataBase.init()
    }

    @Test
    fun saveTest() {
        // given
        val todo = getTodo()

        // when
        val result = todoRepositoryImpl.save(todo)
        // then

        assertThat(result?.index).isEqualTo(1)
        assertThat(result?.title).isEqualTo("Todo")
        assertThat(result?.createdAt).isNotNull
        assertThat(result?.updatedAt).isNotNull
    }

    @Test
    fun saveAllTest() {
        // given
        val todoList = getTodoList()

        // when
        val result = todoRepositoryImpl.saveAll(todoList)

        // then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun findOneTest() {
        // given
        val todoList = getTodoList()

        // when
        todoRepositoryImpl.saveAll(todoList)
        val result = todoRepositoryImpl.findOne(2)
        println(result)

        // then
        assertThat(result).isNotNull
        assertThat(result?.index).isEqualTo(2)
        assertThat(result?.title).isEqualTo("Todo2")
    }

    @Test
    fun updateTest() {
        // given
        val todo = getTodo()
        val saveTodo = todoRepositoryImpl.save(todo)

        // when
        val newTodo = Todo().apply {
            this.index = saveTodo?.index
            this.title = "Update Todo"
            this.description = "Update Test"
            this.schedule = LocalDateTime.now()
            // index, createdAt, updatedAt => DB에서 설정
        }
        val result = todoRepositoryImpl.save(newTodo)

        // then
        assertThat(result?.title).isEqualTo("Update Todo")
        assertThat(result?.description).isEqualTo("Update Test")
    }

    @Test
    fun deleteTest() {
        // given
        val todo = getTodo()
        val saveTodo = todoRepositoryImpl.save(todo)

        // when
        val result = todoRepositoryImpl.delete(saveTodo?.index!!)
        // val result = saveTodo?.index?.let { todoRepositoryImpl.delete(it) }

        // then
        assertThat(result).isEqualTo(true)
        assertThat(todoRepositoryImpl.todoDataBase.todoList.size).isEqualTo(0)
    }

    @Test
    fun findAll() {
        // given
        val todoList = getTodoList()
        todoRepositoryImpl.saveAll(todoList)

        // when
        val result = todoRepositoryImpl.findAll()

        // then
        assertThat(result.size).isEqualTo(3)
        assertThat(result).isEqualTo(todoList)
    }

    private fun getTodo(): Todo {
        return Todo().apply {
            this.title = "Todo"
            this.description = "Test"
            this.schedule = LocalDateTime.now()
            // index, createdAt, updatedAt => DB에서 설정
        }
    }

    private fun getTodoList(): MutableList<Todo> {
        return mutableListOf(
                Todo().apply {
                    this.title = "Todo1"
                    this.description = "Test1"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "Todo2"
                    this.description = "Test2"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "Todo3"
                    this.description = "Test3"
                    this.schedule = LocalDateTime.now()
                }
        )
    }
}