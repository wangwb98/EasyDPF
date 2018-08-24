package com.asrmicro.os.easydpf

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@com.bumptech.glide.annotation.GlideModule
internal class MyGlideModule : AppGlideModule(){
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(String::class.java, java.io.InputStream::class.java, SmbLoader.Factory())
    }
}
