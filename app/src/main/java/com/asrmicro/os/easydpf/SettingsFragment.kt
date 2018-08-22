package com.asrmicro.os.easydpf

import android.os.Bundle
import android.support.v14.preference.PreferenceDialogFragment
import android.support.v14.preference.PreferenceFragment
import android.support.v17.preference.LeanbackPreferenceFragment
import android.support.v17.preference.LeanbackSettingsFragment
import android.app.Fragment
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceScreen
import android.widget.Toast

class SettingsFragment : LeanbackSettingsFragment() {
    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(PrefsFragment())
    }

    override fun onPreferenceStartFragment(caller: PreferenceFragment?, pref: Preference?): Boolean {
        val f = Fragment.instantiate(activity, pref?.fragment, pref?.extras)
        if (caller is Fragment)
            f.setTargetFragment(caller, 0)
        if (f is PreferenceFragment || f is PreferenceDialogFragment)
            startPreferenceFragment(f)
        else
            startImmersiveFragment(f)
        return true
    }

    override fun onPreferenceStartScreen(caller: PreferenceFragment?, pref: PreferenceScreen?): Boolean {
        val f = PrefsFragment()
        val args = Bundle(1)
        args.putString(PreferenceFragment.ARG_PREFERENCE_ROOT, pref?.key)
        f.arguments = args
        startPreferenceFragment(f)
        return true
    }
}

class PrefsFragment : LeanbackPreferenceFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}