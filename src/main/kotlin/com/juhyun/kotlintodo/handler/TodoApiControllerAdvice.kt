package com.juhyun.kotlintodo.handler

import com.juhyun.kotlintodo.controller.api.TodoApiController
import com.juhyun.kotlintodo.model.Error
import com.juhyun.kotlintodo.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(basePackageClasses = [TodoApiController::class])
class TodoApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse>? {
        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }.apply {
                errors.add(this)
            }
        } // forEach end

         val errorResponse = ErrorResponse().apply {
             this.resultCode = "FAIL"
             this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
             this.httpMethod = request.method
             this.message = ""
             this.path = request.requestURI
             this.timestamp = LocalDateTime.now()
             this.errors = errors
         }

        return ResponseEntity.badRequest().body(errorResponse)
    }
}