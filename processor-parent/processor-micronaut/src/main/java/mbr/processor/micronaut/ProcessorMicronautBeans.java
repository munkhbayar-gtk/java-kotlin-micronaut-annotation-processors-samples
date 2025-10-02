package mbr.processor.micronaut;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.util.Set;

@SupportedAnnotationTypes({"mbr.processors.annotations.MicronautBeans"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ProcessorMicronautBeans extends AbstractProcessor {
    private int count = 0;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("ProcessorOne is working " + (++count));
        if(count > 1 || roundEnv.processingOver()) {
            return false;
        }
        try{
            JavaFileObject sourceCodeFile = processingEnv.getFiler().createSourceFile(
                    "my.example.MyCustomMicronautFactory"
            );
            var wr = sourceCodeFile.openWriter();
            wr .write(
                    """
                         package my.example;
                            
                         import io.micronaut.context.annotation.Factory;
                         import io.micronaut.context.annotation.Bean;
                         import jakarta.inject.Singleton;
                         import mbr.processors.beans.MyMicronautBean;
                          
                         @Factory
                         public class MyCustomMicronautFactory {
                         
                            @Singleton
                            public MyMicronautBean micronautBean1() {
                                return new MyMicronautBean();
                            }
                         }"""
            );
            wr.flush();
            wr.close();
        }catch (Exception e ) {
            err("creatingCode", e);
        }
        generateKotlin();
        return false;
    }

    private void generateKotlin () {
        var code = """
                package my.example.generated
                
                import io.micronaut.context.annotation.ConfigurationProperties
                import io.micronaut.context.annotation.Context
                
                @Context
                @ConfigurationProperties("my.test")
                open class MyConfigImpl : my.example.MyConfig() {
                }
                """;
        try {
            var fileObject = processingEnv.getFiler().createResource(
                    StandardLocation.SOURCE_OUTPUT,
                    "my.example.generated",
                    "MyConfigImpl.kt"
            );
            var wr = fileObject.openWriter();
            wr.write(code);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            err("ErrKotlin", e);
        }

    }

    private void err(String msg, Exception e) {
        processingEnv.getMessager().printError("Err: " + msg + "Exception: " + e.getMessage());
    }
    private void log(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
