package com.example.cleanexample.data.mapper

interface Mapper<I,O> {
    fun map(input:I):O
}