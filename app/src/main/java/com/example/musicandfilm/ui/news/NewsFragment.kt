package com.example.musicandfilm.ui.news

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentNewsBinding
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.models.news.Response
import com.example.musicandfilm.services.news.NewsApiInterface
import com.example.musicandfilm.services.news.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class NewsFragment() : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    val arrayList = ArrayList<Items>()
    val displayList = ArrayList<Items>()
    var testing: String = ""
    lateinit var rv_news_list: RecyclerView
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        progressBar = binding.progressBar
        rv_news_list= binding.rvNewsList

        putNewsInRv ()
       refreshApp()
    }



private fun putNewsInRv(){
    progressBar!!.visibility = View.VISIBLE
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            rv_news_list.layoutManager = LinearLayoutManager(activity)
            rv_news_list.setHasFixedSize(true)
            arrayList.clear()
            arrayList.addAll(it)
            displayList.clear()
            displayList.addAll(arrayList)
           // displayList.sortBy { it.text }
            var adapter = NewsAdapter(displayList)
            rv_news_list.adapter = adapter
        })
        viewModel.getAllNews()
    if (rv_news_list.isNotEmpty()) {  progressBar!!.visibility = View.INVISIBLE }
}


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val item = menu!!.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    displayList.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    arrayList.forEach {
                        if (it.text.toLowerCase(Locale.getDefault()).contains(search)) {
                            progressBar!!.visibility = View.INVISIBLE
                            displayList.add(it)
                        }
                    }
                    rv_news_list.adapter!!.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(arrayList)
                    if (rv_news_list.isNotEmpty()) {
                        rv_news_list.adapter!!.notifyDataSetChanged()
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.sort_action ->{
                displayList.sortBy { it.text }
                rv_news_list.adapter!!.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }
   private fun refreshApp(){
       val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
       swipe_to_refresh.setOnRefreshListener {
           putNewsInRv()
           swipe_to_refresh.isRefreshing = false
       }
   }
}