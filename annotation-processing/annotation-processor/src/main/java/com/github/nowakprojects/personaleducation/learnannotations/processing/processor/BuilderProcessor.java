package com.github.nowakprojects.personaleducation.learnannotations.processing.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

//https://www.baeldung.com/java-annotation-processing-builder
@SupportedAnnotationTypes(
        "com.github.nowakprojects.personaleducation.learnannotations.processing.processor.BuilderProperty"
)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
