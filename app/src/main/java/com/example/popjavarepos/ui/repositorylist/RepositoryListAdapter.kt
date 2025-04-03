package com.example.popjavarepos.ui.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popjavarepos.data.model.Repository
import com.example.popjavarepos.databinding.ItemRepositoryBinding

class RepositoryListAdapter(
    private val onItemClick: (Repository) -> Unit
) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() {

    private val repositories = mutableListOf<Repository>()

    fun submitList(newRepositories: List<Repository>) {
        repositories.clear()
        repositories.addAll(newRepositories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository) {
            binding.textViewRepoName.text = repository.name
            binding.textViewRepoDescription.text = repository.description
            binding.textViewStars.text = repository.stars.toString()
            binding.textViewForks.text = repository.forks.toString()
            binding.textViewAuthorName.text = repository.owner.login

            Glide.with(binding.imageViewAuthorAvatar.context)
                .load(repository.owner.avatarUrl)
                .circleCrop()
                .into(binding.imageViewAuthorAvatar)

            binding.root.setOnClickListener {
                onItemClick(repository)
            }
        }
    }
}