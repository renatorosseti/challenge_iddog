package com.rosseti.iddog.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import com.rosseti.iddog.util.InternetUtil
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var internetUtil: InternetUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        Log.i("Fragment", viewModel.test)
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFeedContent()
        viewModel.response.observe(this, Observer {

        })
    }
}