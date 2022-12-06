package com.example.cleanexample.ui.di

interface Factory<T> {
    fun create():T
}