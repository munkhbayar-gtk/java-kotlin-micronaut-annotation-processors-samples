package my.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import mbr.processors.beans.MyMicronautBean;

import java.util.Random;

@Controller("/test")
public class MyController {
    private final MyMicronautBean bean;

    public MyController(MyMicronautBean bean) {
        this.bean = bean;
    }

    @Get
    public Object test() {
        return this.bean.greet();
    }
}
