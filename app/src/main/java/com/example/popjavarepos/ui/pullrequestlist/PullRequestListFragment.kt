package com.example.popjavarepos.ui.pullrequestlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popjavarepos.data.remote.RetrofitClient
import com.example.popjavarepos.data.repository.PullRequestRepository
import com.example.popjavarepos.databinding.FragmentPullRequestListBinding

class PullRequestListFragment : Fragment() {

    private var _binding: FragmentPullRequestListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PullRequestListViewModel
    private lateinit var adapter: PullRequestListAdapter

    companion object {
        private const val ARG_OWNER = "owner"
        private const val ARG_REPO = "repo"

        fun newInstance(owner: String, repo: String): PullRequestListFragment {
            val fragment = PullRequestListFragment()
            val args = Bundle()
            args.putString(ARG_OWNER, owner)
            args.putString(ARG_REPO, repo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPullRequestListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val owner = arguments?.getString(ARG_OWNER) ?: ""
        val repo = arguments?.getString(ARG_REPO) ?: ""

        val api = RetrofitClient.apiService
        val repository = PullRequestRepository(api)
        val factory = PullRequestListViewModelFactory(repository, owner, repo)
        viewModel = ViewModelProvider(this, factory).get(PullRequestListViewModel::class.java)

        adapter = PullRequestListAdapter { pullRequest ->
            val url = pullRequest.htmlUrl
            if (!url.isNullOrEmpty()) {
                Log.d("PullRequestListFragment", "Opening URL: $url")
                val customTabsIntent = CustomTabsIntent.Builder().build()
                try {
                    customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Unable to open link: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "URL is not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerViewPullRequests.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPullRequests.adapter = adapter
        binding.recyclerViewPullRequests.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        viewModel.pullRequests.observe(viewLifecycleOwner) { pullRequests ->
            adapter.submitList(pullRequests)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}