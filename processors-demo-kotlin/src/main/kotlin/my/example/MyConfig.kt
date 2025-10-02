package my.example

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context

//@Context
//@ConfigurationProperties("my.test")
open class MyConfig {
    var url : String = ""
}