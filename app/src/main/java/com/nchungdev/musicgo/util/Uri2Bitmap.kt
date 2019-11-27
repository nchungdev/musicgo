package com.nchungdev.musicgo.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.WorkerThread
import com.nchungdev.musicgo.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object Uri2Bitmap {

    fun getThumbnail(context: Context, uri: Uri, drawableId: Int): Bitmap {
        val input = try {
            context.contentResolver?.openInputStream(uri) ?: return getDefaultBitmap(
                context,
                drawableId
            )
        } catch (e: Exception) {
            return getDefaultBitmap(context, drawableId)
        }
        return input.use {
            BitmapFactory.decodeStream(input)
        }
    }

    private fun getDefaultBitmap(context: Context, drawableId: Int) =
        BitmapFactory.decodeResource(context.resources, drawableId)


    @SuppressLint("CheckResult")
    @WorkerThread
    operator fun invoke(
        context: Context,
        uri: Uri,
        drawableId: Int = R.drawable.ic_play_24dp,
        callback: (Bitmap) -> Unit
    ) {
        Single.fromCallable { getThumbnail(context, uri, drawableId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ callback(it) }, {})
    }
}