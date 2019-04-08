package com.github.nowakprojects.personaleducation.oracletutorial.reflectionapi.classes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReflectionApiSpecification {

    @Nested
    @DisplayName("given string instance")
    class GivenStringInstance {

        private final String instance = "foo";

        @Nested
        @DisplayName("when get class invoked")
        class WhenGetClassInvoked {


            private final Class<?> clazz = instance.getClass();

            @Test
            void shouldReturnInstanceClass() {
                assertEquals(String.class, clazz);
            }


        }
    }

    @Nested
    class GivenEnumInstance {


        private final SampleEnum instance = SampleEnum.A;

        @Nested
        class WhenGetClassInvoked {


            private final Class<?> clazz = instance.getClass();

            @Test
            void shouldReturnInstanceClass() {
                assertEquals(SampleEnum.class, clazz);
            }

        }
    }

    @Nested
    class GivenByteArrayInstance {

        private final byte[] instance = new byte[1024];

        @Nested
        class WhenGetClassInvoked {


            private final Class<?> clazz = instance.getClass();

            @Test
            void shouldReturnInstanceClass() {
                assertEquals(byte[].class, clazz);
            }

        }
    }


    @Nested
    class GivenPrimitiveBooleanClass{

        Class<?> clazz = boolean.class;

        @Nested
        class WhenCompareToBooleanObjectClass{

            @Test
            void classesShouldNotBeEquals(){
                assertNotEquals(Boolean.class, clazz);

            }
        }
    }

    


}
