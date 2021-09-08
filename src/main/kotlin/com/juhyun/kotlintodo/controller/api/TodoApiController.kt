package com.juhyun.kotlintodo.controller.api

import com.juhyun.kotlintodo.datasource.Todo
import com.juhyun.kotlintodo.model.TodoDto
import com.juhyun.kotlintodo.service.TodoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "일정관리")
@RestController
@RequestMapping("/api/todo")
class TodoApiController(val todoService: TodoService) {

    // Create
    @ApiOperation("일정 생성")
    @PostMapping
    fun create(@Valid @RequestBody todoDto: TodoDto): Todo? {
        return todoService.create(todoDto)
    }

    @ApiOperation("특정 일정 조회")
    // Read
    @GetMapping
    fun read(
            @ApiParam(name = "index")
            @RequestParam(required = false) index: Int?): ResponseEntity<Any?> {
        return index?.let {
            todoService.read(index)
        }?.let {
            return ResponseEntity.ok(it)
        } ?: kotlin.run {
            return ResponseEntity
                    .status(HttpStatus.MOVED_PERMANENTLY)
                    .header(HttpHeaders.LOCATION, "/api/todo/all")
                    .build()
        }
    }

    @ApiOperation("전체 일정 조회")
    @GetMapping("/all")
    fun readAll(): MutableList<Todo> {
        return todoService.readAll()
    }

    @ApiOperation("일정 수정")
    // Update TODO: create = 201, update = 200
    @PutMapping
    fun update(@Valid @RequestBody todoDto: TodoDto): Todo? {
        return todoService.create(todoDto)
    }

    @ApiOperation("일정 삭제")
    // Delete
    @DeleteMapping("/{index}")
    fun delete(@PathVariable index: Int): ResponseEntity<Any> {
        if (!todoService.delete(index)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

        return ResponseEntity.ok().build()
    }

}