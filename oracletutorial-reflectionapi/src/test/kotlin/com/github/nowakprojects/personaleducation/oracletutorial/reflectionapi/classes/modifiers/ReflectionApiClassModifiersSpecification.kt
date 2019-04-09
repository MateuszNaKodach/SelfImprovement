package com.github.nowakprojects.personaleducation.oracletutorial.reflectionapi.classes.modifiers

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.reflect.Modifier

class ReflectionApiClassModifiersSpecification {

    @Nested
    inner class GivenInterfaceClass {

        val interfaceClass = ExampleInterface::class.java

        @Nested
        inner class WhenGetModifiersIsInvoked {

            val result = Modifier.toString(interfaceClass.modifiers)

            @Test
            fun `then result should always contains "abstract" modifier`() {
                then(result).contains("abstract")
            }

        }

    }
}