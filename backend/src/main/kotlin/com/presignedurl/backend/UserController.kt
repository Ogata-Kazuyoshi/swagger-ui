package com.presignedurl.backend

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/users")
class UserController {
    @GetMapping("/{id}" , produces = ["application/json"])
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "OK!!!",
            content = [Content(schema = Schema(implementation = ResponseGetUser::class))]
        ),
//        ApiResponse(
//            responseCode = "404",
//            description = "Not Found",
//            content = [Content(schema = Schema(implementation = ResponceError::class))]
//        )
    )
    fun getUsers (
        @PathVariable id: Int
    ):ResponseGetUser {
        if (id == 1) {
            return ResponseGetUser(
                id =  id,
                name = "Tanaka Taro",
                birth = "19900530",
                gender = "Male"
            )
        }

        throw UserNotFoundException("User Not Found")
    }

    @ApiResponse(
        responseCode = "403",
        description = "Forbidden - CSRF token missing or incorrect",
        content = [Content(schema = Schema(implementation = ResponceError::class))]
    )
    @PostMapping
    fun postUser (
        @RequestBody reqBody:RequestPostUser,
        @RequestHeader("X-CSRF-TOKEN") csrfToken: String
    ):ResponsePostUser {
        return ResponsePostUser(
            age = reqBody.age,
            name = "Tanaka Jiro"
        )
    }
}


data class ResponseGetUser (
    @field:Schema(example = "100")
    val id : Int,
    @field:Schema(example = "Tanaka Taro")
    val name : String,
    @field:Schema(example = "20200930")
    val birth : String,
    @field:Schema(example = "Male")
    val gender : String
)

data class ResponceError (
    @field:Schema(example = "Not Found")
    val message: String
)

data class RequestPostUser (
    val age:Int
)

data class ResponsePostUser (
    @field:Schema(example = "50")
    val age:Int,
    @field:Schema(example = "Tanaka Saburo")
    val name: String
)