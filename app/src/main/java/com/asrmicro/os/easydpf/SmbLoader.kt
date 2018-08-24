package com.asrmicro.os.easydpf

import android.content.Context
import android.renderscript.ScriptGroup
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import jcifs.smb.SmbFile
import java.io.InputStream
import java.io.IOException

class SmbLoader : ModelLoader<String,InputStream> {
    var model : String = ""
    override fun handles(model: String): Boolean {
        if (model.startsWith("smb://"))
            return true
        else
            return false
    }
    override fun buildLoadData(model: String, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData<InputStream>(ObjectKey(model), SmbDataFetcher(model))
    }

    class SmbDataFetcher (var model: String) : DataFetcher <InputStream> {
        var stream: InputStream? = null

        override fun getDataClass(): Class<InputStream> {
            return InputStream::class.java
        }

        override fun getDataSource(): DataSource {
            return DataSource.REMOTE
        }

        override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
            stream = SmbFile(model).inputStream
            callback.onDataReady(stream)
        }
        override fun cleanup() {
            if (stream == null) return
            try {
                stream!!.close()
                stream = null
            } catch ( e: IOException) {
                e.printStackTrace()
            }
        }
        override fun cancel() { /* do nothing */            }
    }


    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return SmbLoader()
        }
        override fun teardown() {
            /* do nothing */
        }
    }
}