package io.github.nowakprojects.learning.annotations.jsonserializer

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class JsonFieldKt(
    val value: String = ""
)
