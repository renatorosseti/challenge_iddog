package com.rosseti.iddog.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rosseti.iddog.R
import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import com.rosseti.iddog.main.details.DetailsActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var errorDialog: ErrorDialog

    lateinit var mainAdapter: MainAdapter

    lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        setLayoutManager()
        observeViewModel()
        category = NavHostFragment.findNavController(this).currentDestination!!.label.toString().toLowerCase()
        viewModel.fetchFeedContent(category = category)
    }

    private fun setLayoutManager() {
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.response.observe(this, Observer {
            when (it) {
                is MainViewState.ShowLoadingState -> progressDialog.show(context = context!!)
                is MainViewState.ShowContentFeed -> {
                    progressDialog.hide()
                    updateAdapter(it.images)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.frag_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_retry -> viewModel.fetchFeedContent(category = category)
        }
        return true
    }

    private fun updateAdapter(images: List<String>) {
        mainAdapter = MainAdapter(images)
        recyclerView.adapter = mainAdapter
        mainAdapter?.onItemClick = { it -> run {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("imageUrl", it)
            startActivity(intent)
        }
        }
    }
}