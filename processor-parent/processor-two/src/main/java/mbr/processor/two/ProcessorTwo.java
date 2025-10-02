package mbr.processor.two;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Set;

@SupportedAnnotationTypes({"mbr.processors.annotations.ProcessorOne"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ProcessorTwo extends AbstractProcessor {
    private int count = 0;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("ProcessorTwo is working " + (++count));
        if(count > 1 || roundEnv.processingOver()) {
            return false;
        }
        try{
            JavaFileObject sourceCodeFile = processingEnv.getFiler().createSourceFile(
                    "my.example.MyProcessorTwo"
            );
            var wr = sourceCodeFile.openWriter();
            wr .write(
                    """
                         package my.example;
                         
                         public class MyProcessorTwo {
                            public String greet() {
                                return "Greeting from my.example.MyProcessorTwo";
                            }
                         }"""
            );
            wr.flush();
            wr.close();
        }catch (Exception e ) {
            err("creatingCode", e);
        }


        return false;
    }
    private void err(String msg, Exception e) {
        processingEnv.getMessager().printError("Err: " + msg + "Exception: " + e.getMessage());
    }
    private void log(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
