package com.app.androidPixabay.ui.pixabay.list

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.androidPixabay.R
import com.app.androidPixabay.commons.communicator.HitsItem
import com.app.androidPixabay.commons.utils.Constants
import com.app.androidPixabay.commons.utils.Constants.Companion.EXTRA_QUERY
import com.app.androidPixabay.commons.utils.Constants.Companion.FRUITS_QUERY_TAG
import com.app.androidPixabay.commons.utils.UiHelper
import com.app.androidPixabay.datasource.api.NetworkState
import com.app.androidPixabay.domain.entities.HitsList
import com.app.androidPixabay.ui.pixabay.details.PixaBayDetailsActivity
import com.app.androidPixabay.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_pixabay_list.*
import kotlinx.android.synthetic.main.view_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PixabayListActivity : AppCompatActivity(), View.OnClickListener, HitsItem {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
    private val uiHelper : UiHelper by inject()
    private val pixabayListVM : PixabayListVM by viewModel()
    private val pixabayAdapter = PixabayAdapter()
    private var query : String = FRUITS_QUERY_TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixabay_list)

        savedInstanceState?.getString(EXTRA_QUERY)?.let { query = it }

        initRecyclerView()

        /*
         * Check Internet Connection
         * */

        if(uiHelper.getConnectivityStatus()) subscribeObservables(query)
        else uiHelper.showSnackBar(main_rootView, resources.getString(R.string.error_message_network))

        execute_search_button.setOnClickListener(this)
    }

    private fun subscribeObservables(query : String) {
        pixabayListVM.fetchKeyQuery(query)
        pixabayListVM.pixabayData.observe(this, Observer { pixabayAdapter.submitList(it) })

        pixabayListVM.networkState?.observe(this, Observer {

            /*
             * Progress Updater
             * */

            it?.let {
                when(it) {
                    is NetworkState.Loading -> {
                        EspressoIdlingResource.increment()
                        showProgressBar(true)
                    }
                    is NetworkState.Success -> {
                        EspressoIdlingResource.decrement()
                        showProgressBar(false)
                    }
                    is NetworkState.Error -> showProgressBar(false)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.execute_search_button -> {
                if(!TextUtils.isEmpty(search_input_text.text.toString())) {
                    uiHelper.hideKeyBoard(v)
                    query = search_input_text.text.toString()
                    searchQuery()
                }
                else uiHelper.showSnackBar(main_rootView,resources.getString(R.string.enter_query_to_search))
            }
        }
    }

    private fun searchQuery() {
        close_search_button.performClick()
        subscribeObservables(query)
    }

    // Save data to Bundle when screen rotates
    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(EXTRA_QUERY,query)
    }

    // Restore data from Bundle when screen rotates
    override fun onRestoreInstanceState(savedInstanceState : Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getString(EXTRA_QUERY)?.let { query = it }
    }

    // Setup the adapter class for the RecyclerView
    private fun initRecyclerView() {
        recylv_pixabay.layoutManager = GridLayoutManager(this, 2)
        recylv_pixabay.adapter = pixabayAdapter
        pixabayAdapter.setOnHitsItemClickListener(this)
    }

    // UPDATE UI ----
    private fun showProgressBar(display : Boolean) {
        if(!display) progress_bar.visibility = View.GONE
        else progress_bar.visibility = View.VISIBLE
    }

    override fun onHitsItemClickListener(hitsList: HitsList?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.show_details)
        alertDialog.setMessage(R.string.show_more_details)
        alertDialog.setPositiveButton(R.string.yes) { _, _ ->

            hitsList?.let {
                val intent = Intent(this, PixaBayDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_HITSLIST, hitsList)
                startActivity(intent)
            }
        }
        alertDialog.setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }
}
