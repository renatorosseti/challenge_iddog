package com.rosseti.iddog.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rosseti.iddog.R
import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import com.rosseti.iddog.main.MainActivity
import com.rosseti.iddog.main.details.DetailsActivity
import com.rosseti.iddog.util.InternetUtil
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var internetUtil: InternetUtil

    @Inject
    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var errorDialog: ErrorDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        val category = NavHostFragment.findNavController(this).currentDestination!!.label.toString()
        viewModel.fetchFeedContent(category = category.toLowerCase())

        viewModel.response.observe(this, Observer {
            when(it) {
                is MainViewState.ShowLoadingState -> progressDialog.show(context = context!!)
                is MainViewState.ShowContentFeed -> {
                    progressDialog.hide()
                    setAdapter(it.images)
                }
                is MainViewState.ShowRequestError -> {
                    progressDialog.hide()
                    errorDialog.show(
                        context = context!!,
                        message = getString(it.message)
                    )
                }
            }
        })
    }

    private fun setAdapter(images: List<String>) {
        val mainAdapter = MainAdapter(images = images)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mainAdapter
        mainAdapter?.onItemClick = { it -> run {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("imageUrl", it)
            startActivity(intent)
        }
        }
    }
}