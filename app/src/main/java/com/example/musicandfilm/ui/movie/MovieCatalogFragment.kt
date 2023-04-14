package com.example.musicandfilm.ui.movie

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentMainBinding
import com.example.musicandfilm.models.movies.Movie
import com.example.musicandfilm.ui.profile.ProfileViewModel
import java.util.*
import kotlin.collections.ArrayList


class MovieCatalogFragment() : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    val arrayList = ArrayList<Movie>()
    val displayList = ArrayList<Movie>()
    val appContext = context
    lateinit var rv_movies_list: RecyclerView
    //  private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

   override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
       super.onViewCreated(itemView, savedInstanceState)
       rv_movies_list = binding.rvMoviesList
        putMoviesInRv()
       refreshApp()
   }

    private fun putMoviesInRv(){
        val viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        val viewModel1 = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel1.initDatabase()
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            rv_movies_list.layoutManager = LinearLayoutManager(activity)
            rv_movies_list.setHasFixedSize(true)
            arrayList.clear()
            arrayList.addAll(it)
            displayList.clear()
            displayList.addAll(arrayList)
            var adapter = MovieAdapter(displayList)
            rv_movies_list.adapter = adapter
        })
        viewModel.getAllMovies()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
       val item = menu!!.findItem(R.id.search_action)
        if (item!=null) {

        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.search_action -> {
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
                            rv_movies_list.adapter!!.notifyDataSetChanged()
                        } else {
                            displayList.clear()
                            displayList.addAll(arrayList)
                            rv_movies_list.adapter!!.notifyDataSetChanged()
                        }
                        return true
                    }
                })
                return true
            }
            R.id.search_action ->{
                displayList.sortBy { it.title }
                rv_movies_list.adapter!!.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun refreshApp(){
        val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
        swipe_to_refresh.setOnRefreshListener {
            putMoviesInRv()
            swipe_to_refresh.isRefreshing = false
        }
    }
}