package com.example.musicandfilm.ui.movie

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.view.isNotEmpty
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
    private var progressBar: ProgressBar? = null
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
       progressBar = binding.progressBar
       rv_movies_list = binding.rvMoviesList
        putMoviesInRv()
       refreshApp()
   }

    private fun putMoviesInRv(){
       progressBar!!.visibility = View.VISIBLE
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
        if (rv_movies_list.isNotEmpty()) {  progressBar!!.visibility = View.INVISIBLE }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
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
                               progressBar!!.visibility = View.INVISIBLE
                               displayList.add(it)
                           }
                       }
                       rv_movies_list.adapter!!.notifyDataSetChanged()
                   } else {
                       displayList.clear()
                       displayList.addAll(arrayList)
                       if (rv_movies_list.isNotEmpty()) {
                           rv_movies_list.adapter!!.notifyDataSetChanged()
                       }
                   }
                   return true
               }
           })
       }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.sort_action ->{
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