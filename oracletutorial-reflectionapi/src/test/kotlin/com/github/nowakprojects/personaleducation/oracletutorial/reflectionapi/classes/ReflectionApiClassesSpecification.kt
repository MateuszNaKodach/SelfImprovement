package com.github.nowakprojects.personaleducation.oracletutorial.reflectionapi.classes

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.reflect.Field

class ReflectionApiClassesSpecification {

    @Nested
    inner class GivenExampleClassInstance {

        private val example = Example()

        @Test
        fun `test field enclosing class should be Example`(){
            val field: Field = example.javaClass.getField("test")
            then(field.declaringClass).isEqualTo(example.javaClass)
        }


    }
}