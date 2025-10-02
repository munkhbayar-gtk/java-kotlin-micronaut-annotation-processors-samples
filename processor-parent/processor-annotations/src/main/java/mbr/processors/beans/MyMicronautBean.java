package mbr.processors.beans;

import java.util.Map;
import java.util.Random;

public class MyMicronautBean {
    public Object greet() {
        return Map.of(
                "num", new Random().nextInt(10000)

        );
    }
}
