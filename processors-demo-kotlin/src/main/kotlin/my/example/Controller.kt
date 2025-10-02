package my.example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mbr.processors.beans.MyMicronautBean
import kotlin.random.Random

@Controller("/test")
class Controller(
    private val bean : MyMicronautBean,
    private val cfg : MyConfig
) {
    @Get
    fun greet() : Any {
        return mapOf(
            "my-num" to "${Random.nextInt(10000)}",
            "my-bean" to bean.greet(),
            "cfg" to cfg.url
        )
    }
}