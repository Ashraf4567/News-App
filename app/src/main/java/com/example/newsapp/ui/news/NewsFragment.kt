package com.example.newsapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.api.model.sourcesResponse.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.home.newsDetails.NewsDetailsActivity
import com.example.newsapp.ui.showMessage
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    var pageSize = 20
    var currentPage = 1
    lateinit var sourceObj: Source
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNewsSources()
        initObservers()
        initViews()
    }

    private fun initObservers() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)
            getNews()
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            adapter.bindNews(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleError(it)
        }

    }

    private fun getNews() {
        viewModel.getNews(sourceObj.id, pageSize = pageSize, page = currentPage)
        isLoading = false

    }

    val adapter = NewsAdapter()

    private fun initViews() {
        viewBinding.recyclerView.adapter = adapter
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this

        //pageSize
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManager = recyclerView.layoutManager as LinearLayoutManager
                var lastVisibleItemCount = layoutManager.findLastVisibleItemPosition()
                var totalItemCount = layoutManager.itemCount
                var visibleThreshold = 3

                if (isLoading && totalItemCount - lastVisibleItemCount <= visibleThreshold) {
                    isLoading = true
                    currentPage++
                    getNews()
                }
            }
        })

        adapter.onNewsClick = NewsAdapter.OnNewsClick { news ->
            var intent = Intent(requireContext(), NewsDetailsActivity::class.java)
            intent.putExtra("news", news)
            startActivity(intent)
        }

    }


    private fun bindTabs(newsSources: List<Source?>?) {
        if (newsSources == null) return
        newsSources.forEach { source ->
            val tap = viewBinding.tapLayout.newTab()
            tap.text = source?.name
            tap.tag = source
            viewBinding.tapLayout.addTab(tap)

        }
        viewBinding.tapLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    sourceObj = source
                    viewModel.getNews(source.id, pageSize = pageSize, page = currentPage)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                    val source = tab?.tag as Source
                    sourceObj = source
                    viewModel.getNews(source.id, pageSize = pageSize, page = currentPage)
                }
            }
        )
        viewBinding.tapLayout.getTabAt(0)?.select()
    }


    fun handleError(viewError: ViewError) {
        showMessage(message = viewError.message ?: viewError.throwable?.localizedMessage
        ?: "something went wrong",
            posActionName = "try again",
            posAction = { dialogInterface, i ->
                dialogInterface.dismiss()
                viewError.onTryAgainClickListener.onTryAgainClick()
            }, nagActionName = "cancel",
            negAction = { dialogInterface, i ->
                dialogInterface.dismiss()
            })
    }
}


