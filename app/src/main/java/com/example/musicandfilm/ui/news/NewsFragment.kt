package com.example.musicandfilm.ui.news

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
        val cringe: TextView = binding.cringetest

        putNewsInRv ()
        refreshApp()
    }



private fun putNewsInRv(){
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        val rv_news_list: RecyclerView = binding.rvNewsList
        val cringe: TextView = binding.cringetest
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            rv_news_list.layoutManager = LinearLayoutManager(activity)
            rv_news_list.setHasFixedSize(true)
            arrayList.clear()
            arrayList.addAll(it)
            displayList.clear()
            displayList.addAll(arrayList)
            var adapter = NewsAdapter(displayList)
            rv_news_list.adapter = adapter
        })
        viewModel.getNewsData()
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val rv_news_list: RecyclerView = binding.rvNewsList
        val item = menu!!.findItem(R.id.search_action)
        if (item!=null) {
            val searchView = item?.actionView as SearchView
            //   displayList.addAll(it)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        arrayList.forEach {
                            if (it.title.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                                //  Log.d("MyLog", "test " + it.toString())
                            }
                        }
                        rv_news_list.adapter!!.notifyDataSetChanged()
                    } else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                        // rv_events_list.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }*/
   private fun refreshApp(){
       val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
       swipe_to_refresh.setOnRefreshListener {
           putNewsInRv()
           swipe_to_refresh.isRefreshing = false
       }
   }
}