package com.asrmicro.os.easydpf

import android.content.Context
import com.bumptech.glide.Priority
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GenericLoaderFactory
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.stream.StreamModelLoader
import jcifs.smb.SmbFile
import java.io.InputStream
import java.io.IOException

class SmbLoader : StreamModelLoader<String> {
    override public fun getResourceFetcher(uri: String?, width: Int, height: Int): DataFetcher<InputStream> {
        return object : DataFetcher <InputStream> {
            var stream : InputStream ? = null

            /**
             * Asynchronously fetch data from which a resource can be decoded. This will always be called on
             * background thread so it is safe to perform long running tasks here. Any third party libraries called
             * must be thread safe since this method will be called from a thread in a
             * [java.util.concurrent.ExecutorService] that may have more than one background thread.
             *
             * This method will only be called when the corresponding resource is not in the cache.
             *
             *
             *
             * Note - this method will be run on a background thread so blocking I/O is safe.
             *
             *
             * @param priority The priority with which the request should be completed.
             * @see .cleanup
             */
            override fun loadData(priority: Priority?): InputStream {
                stream = SmbFile(uri).inputStream
                return stream!!
            }

            /**
             * Cleanup or recycle any resources used by this data fetcher. This method will be called in a finally block
             * after the data returned by [.loadData] has been decoded by the
             * [com.bumptech.glide.load.ResourceDecoder].
             *
             *
             *
             * Note - this method will be run on a background thread so blocking I/O is safe.
             *
             *
             */
            override fun cleanup() {
                if (stream == null) return
                try {
                    stream!!.close()
                    stream = null
                } catch ( e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun getId(): String {
                return uri!!
            }
            override fun cancel() { /* do nothing */            }
        }
    }

    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(context: Context?, factories: GenericLoaderFactory?): ModelLoader<String, InputStream> {
            return SmbLoader()
        }

        override fun teardown() {
            /* do nothing */
        }
    }
}