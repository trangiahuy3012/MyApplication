package com.example.myapplication.screens.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.data.Movie
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewModels.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: HomeAdapter
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeNav.setOnItemSelectedListener {
            viewModel.setTabID(it.itemId)
            true
        }
        setUpRecyclerView()
        setUpViewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "My movies"
    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RestaurantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun setUpViewModelObservers() {
        viewModel.menuLayoutType.observe(viewLifecycleOwner){
            binding.rvRestaurant.layoutManager = activity?.let { it1 -> viewModel.getLayout(it1) }
        }

        viewModel.newList.observe(viewLifecycleOwner){
            adapter.submitList(it as MutableList<Movie>)
        }

        viewModel.tabID.observe(viewLifecycleOwner){
            viewModel.getMovies()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvRestaurant.layoutManager = activity?.let { viewModel.getLayout(it) }
        adapter = HomeAdapter(onRestaurantItemClick)
        binding.rvRestaurant.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menu_item_linear -> viewModel.setLayout(item.itemId)
            R.id.menu_item_grid -> viewModel.setLayout(item.itemId)
            R.id.menu_item_avatar -> {
                val controller = findNavController()
                controller.navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val onRestaurantItemClick = object : OnHomeItemClick{
        override fun onClickItem(item: View, restaurant: Movie) {
            val controller = findNavController()
            viewModel.handleItemWhenClicked(item, restaurant, controller)

        }

        override fun onLongClick(item: View) {
            viewModel.handleItemWhenLongClicked(item)
        }
    }

}