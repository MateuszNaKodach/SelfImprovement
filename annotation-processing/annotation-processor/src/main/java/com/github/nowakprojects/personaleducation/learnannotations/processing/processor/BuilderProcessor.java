package com.github.nowakprojects.personaleducation.learnannotations.processing.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

//https://www.baeldung.com/java-annotation-processing-builder
@SupportedAnnotationTypes(
        "com.github.nowakprojects.personaleducation.learnannotations.processing.processor.BuilderProperty"
)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement annotation : set) {
            Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(annotation);
            final var annotatedMethods = annotatedElements.stream()
                    .collect(
                            Collectors.partitioningBy(
                                    element ->
                                            ((ExecutableType) element.asType()).getParameterTypes().size() == 1
                                                    && element.getSimpleName().toString().startsWith("set")
                            )
                    );
            final var setters = annotatedMethods.get(Boolean.TRUE);
            final var invalidAnnotatedMethods = annotatedMethods.get(Boolean.FALSE);

            invalidAnnotatedMethods.forEach(element ->
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "@BuilderProperty must be applied to a setXxx method with a signle argument", element)
            );
            if (!setters.isEmpty()) {
                final var className = ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();

                final var setterMap = setters.stream()
                        .collect(Collectors.toMap(
                                setter -> setter.getSimpleName().toString(),
                                setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()
                        ));
                try {
                    BuilderFileGenerator.writeBuilderFile(className, setterMap, processingEnv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return true;
    }

}
