package mbr.processors.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})  // can be applied to classes and methods
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorTwo {
}
