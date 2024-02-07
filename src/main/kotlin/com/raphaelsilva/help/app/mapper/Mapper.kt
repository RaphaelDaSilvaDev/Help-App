package com.raphaelsilva.help.app.mapper

interface Mapper<T, U>  {
    fun map(t: T): U
}

interface SpecialMapper<T,C,U> {
    fun map(t: T, c: C?): U
}