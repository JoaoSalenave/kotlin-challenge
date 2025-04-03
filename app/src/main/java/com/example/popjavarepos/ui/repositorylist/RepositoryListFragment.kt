package com.example.popjavarepos.ui.repositorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.popjavarepos.R
import com.example.popjavarepos.data.remote.RetrofitClient
import com.example.popjavarepos.data.repository.RepositoryListRepository
import com.example.popjavarepos.databinding.FragmentRepositoryListBinding
import com.example.popjavarepos.ui.pullrequestlist.PullRequestListFragment
import com.example.popjavarepos.utils.EndlessScrollListener

class RepositoryListFragment : Fragment() {

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RepositoryListViewModel
    private lateinit var adapter: RepositoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val api = RetrofitClient.apiService
        val repository = RepositoryListRepository(api)
        viewModel = ViewModelProvider(
            this,
            RepositoryListViewModelFactory(repository)
        ).get(RepositoryListViewModel::class.java)

        adapter = RepositoryListAdapter { repository ->
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    PullRequestListFragment.newInstance(
                        repository.owner.login,
                        repository.name
                    )
                )
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewRepositories.adapter = adapter
        binding.recyclerViewRepositories.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                viewModel.loadRepositories()
            }
        })

        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            adapter.submitList(repositories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}