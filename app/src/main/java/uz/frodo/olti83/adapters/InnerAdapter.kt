package uz.frodo.olti83.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.frodo.olti83.databinding.InItemBinding
import uz.frodo.olti83.listeners.InnerInterface
import uz.frodo.olti83.room.entities.Module

class InnerAdapter(val innerInterface: InnerInterface):ListAdapter<Module,InnerAdapter.InnerVH>(InnerDiffUtil()) {
    inner class InnerVH(val binding: InItemBinding):ViewHolder(binding.root)

    class InnerDiffUtil:DiffUtil.ItemCallback<Module>() {
        override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerVH {
        val binding = InItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InnerVH(binding)
    }

    override fun onBindViewHolder(holder: InnerVH, position: Int) {
        holder.binding.moduleName.text = getItem(position).name
        holder.binding.cardView.setOnClickListener {
            innerInterface.itemClick(getItem(position))
        }
    }


}