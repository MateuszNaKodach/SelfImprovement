package com.github.nowakprojects.personaleducation.learnannotations.processing.processor

import java.io.IOException
import java.io.PrintWriter
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.JavaFileObject

internal class BuilderFileGenerator {
    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun writeBuilderFile(
                className: String, setterMap: Map<String, String>, processingEnvironment: ProcessingEnvironment
        ) {
            val packageName: String = className.lastIndexOf('.')
                    .let { lastDotIndex -> if (lastDotIndex > 0) className.substring(0, lastDotIndex) else "" }
            val simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            val builderClassName = className + "Builder";
            val builderSimpleClassName = builderClassName.substring(className.lastIndexOf('.') + 1)
            val builderFile: JavaFileObject = processingEnvironment.filer.createSourceFile(builderClassName)

            PrintWriter(builderFile.openWriter())
                    .use {
                        if (packageName.isNotBlank()) {
                            it.print("package $packageName;")
                        }
                        it.print("""
                            public class $builderClassName {
                                private $simpleClassName object = new $simpleClassName();
                                public build(){
                                    return object;
                                }
                                ${
                        setterMap.entries.map { setter ->
                            val methodName = setter.key
                            val argumentType = setter.value
                            {
                                """
                                public $builderSimpleClassName $methodName($argumentType value){
                                    object.$methodName(value);
                                    return this;
                                }
                            """.trimIndent()
                            }
                        }
                        }
                            }
                        """.trimIndent())
                    }
        }
    }
}