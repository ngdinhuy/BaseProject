package com.example.baseproject.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemTestPagerBinding

class TestPagerAdapter(
    private val items: List<TestPagerItem>
) : RecyclerView.Adapter<TestPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemTestPagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PagerViewHolder(
        private val binding: ItemTestPagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TestPagerItem) {
            binding.tvPageNumber.text = item.title
            binding.root.setBackgroundColor(item.backgroundColor)
        }
    }
}

data class TestPagerItem(
    val title: String,
    val backgroundColor: Int
)