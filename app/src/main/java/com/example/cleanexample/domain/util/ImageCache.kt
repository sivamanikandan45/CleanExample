package com.example.cleanexample.domain.util

import android.graphics.Bitmap
import android.util.LruCache

object ImageCache {

    fun getBitmapFromMemCache(imageKey: String): Bitmap? {
        return memoryCache.get(imageKey)
    }

    fun addBitmapToCache(key: String, bitmap: Bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/8th of the available memory for this memory cache.
    val cacheSize = maxMemory / 5

    val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }


}