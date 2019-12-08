package com.github.nowakprojects.personaleducation.kotlin.domainspecificlanguage.pluralsight.model

/**
 * tracks symbols named by strings
 */
class StringSymbolTable<T> {
    var map = mutableMapOf<String, T>()
    // map by types
    fun lookup(symbol: String): T =
            map[symbol] ?: throw CastleSymbolNotRecognizedException("Cannot find a symbol ${symbol}")


    fun add(key: String, value: T?) {
        value?.let {
            map[key] = it
        }
    }
}

class CastleSymbolNotRecognizedException(message: String) : Exception(message)