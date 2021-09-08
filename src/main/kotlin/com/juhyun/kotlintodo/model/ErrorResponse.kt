package com.juhyun.kotlintodo.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

/*
{
    "result_code" : "OK",
    "http_status" : "200",
    "http_method" : "",
    "message" : "성공",
    "path" : "/api/todo",
    "timestamp" : "2021-09-08T09:28:00",
    "errors" :
    [
        {
            "field" : "_name",
            "message" : "5글자 이상이어야 합니다.",
            "value" : ""
        }
    ]
}
 */
data class ErrorResponse(
        @JsonProperty("result_code")
        var resultCode:String? = null,

        @JsonProperty("http_status")
        var httpStatus:String? = null,

        @JsonProperty("http_method")
        var httpMethod:String? = null,

        var message:String? = null,

        var path:String? = null,

        var timestamp:LocalDateTime? = null,

        var errors:MutableList<Error>? = null
)

data class Error(
        var field:String? = null,

        var message: String? = null,

        var value: Any? = null
)