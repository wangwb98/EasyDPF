package com.asrmicro.os.easydpf

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.GlideModule
import java.io.InputStream

class MyGlideModule : GlideModule {
    /**
     * Lazily apply options to a [com.bumptech.glide.GlideBuilder] immediately before the Glide singleton is
     * created.
     *
     *
     *
     * This method will be called once and only once per implementation.
     *
     *
     * @param context An Application [android.content.Context].
     * @param builder The [com.bumptech.glide.GlideBuilder] that will be used to create Glide.
     */
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        /* to add */
    }

    /**
     * Lazily register components immediately after the Glide singleton is created but before any requests can be
     * started.
     *
     *
     *
     * This method will be called once and only once per implementation.
     *
     *
     * @param context An Application [android.content.Context].
     * @param glide The newly created Glide singleton.
     */
    override fun registerComponents(context: Context?, glide: Glide?) {
        if (glide != null ) {
            glide.register(String::class.java, InputStream::class.java, SmbLoader.Factory())
        }
    }
}
