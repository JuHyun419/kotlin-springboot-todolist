package com.juhyun.kotlintodo.config

import com.juhyun.kotlintodo.datasource.TodoDataBase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean(initMethod = "init") // initMethod: TodoDatabase 클래스의 init() 메소드
    fun todoDataBase(): TodoDataBase {
        return TodoDataBase()
    }
}