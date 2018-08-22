package com.asrmicro.os.easydpf

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

        setContentView(R.layout.activity_settings)
    }
}
