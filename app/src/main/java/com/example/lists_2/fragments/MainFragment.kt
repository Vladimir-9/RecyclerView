package com.example.lists_2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lists_2.R
import com.example.lists_2.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        buttonOne = binding!!.btOpenRwOne
        buttonTwo = binding!!.btOpenRwTwo
        buttonThree = binding!!.btOpenRwThree
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonOne.setOnClickListener {
            if (childFragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT) == null) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, ListCarFragment(), TAG_LIST_FRAGMENT)
                    .commit()
            }
        }

        buttonTwo.setOnClickListener {
            if (childFragmentManager.findFragmentByTag(TAG_GRID_LIST_FRAGMENT) == null) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, GridListCarFragment(), TAG_GRID_LIST_FRAGMENT)
                    .commit()
            }
        }

        buttonThree.setOnClickListener {
            if (childFragmentManager.findFragmentByTag(TAG_STAGGERED_LIST_FRAGMENT) == null) {
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.fl_container,
                        StaggeredListCarFragment(),
                        TAG_STAGGERED_LIST_FRAGMENT
                    )
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TAG_LIST_FRAGMENT = "listFragment"
        const val TAG_GRID_LIST_FRAGMENT = "gridListFragment"
        const val TAG_STAGGERED_LIST_FRAGMENT = "staggeredListFragment"
    }
}