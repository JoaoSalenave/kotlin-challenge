package com.example.popjavarepos.ui.pullrequestlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popjavarepos.data.model.PullRequest
import com.example.popjavarepos.databinding.ItemPullRequestBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PullRequestListAdapter(
    private val onItemClick: (PullRequest) -> Unit
) : RecyclerView.Adapter<PullRequestListAdapter.PullRequestViewHolder>() {

    private val pullRequests = mutableListOf<PullRequest>()

    fun submitList(newPullRequests: List<PullRequest>) {
        pullRequests.clear()
        pullRequests.addAll(newPullRequests)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding = ItemPullRequestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PullRequestViewHolder(binding)
    }

    override fun getItemCount(): Int = pullRequests.size

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(pullRequests[position])
    }

    inner class PullRequestViewHolder(private val binding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pullRequest: PullRequest) {
            binding.textViewTitle.text = pullRequest.title
            binding.textViewBody.text = pullRequest.body.orEmpty()
            binding.textViewDate.text = formatDate(pullRequest.createdAt)
            binding.textViewAuthorName.text = pullRequest.user.login

            Glide.with(binding.imageViewAuthorAvatar.context)
                .load(pullRequest.user.avatarUrl)
                .circleCrop()
                .into(binding.imageViewAuthorAvatar)

            binding.root.setOnClickListener {
                onItemClick(pullRequest)
            }
        }

        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                outputFormat.format(date)
            } catch (e: Exception) {
                dateString
            }
        }
    }
}