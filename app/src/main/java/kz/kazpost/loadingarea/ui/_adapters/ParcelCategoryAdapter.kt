package kz.kazpost.loadingarea.ui._adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import kz.kazpost.loadingarea.databinding.ItemParcelTypeBinding
import kz.kazpost.loadingarea.ui._models.ParcelCategoryModel

class ParcelCategoryAdapter:
    RecyclerView.Adapter<ParcelCategoryAdapter.ParcelCategoryViewHolder>() {

    private val listDiffer = AsyncListDiffer(this, ParcelCategoryModel.ItemCallback())

    fun submitList(list: List<ParcelCategoryModel>) {
        listDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParcelCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemParcelTypeBinding.inflate(inflater, parent, false)
        return ParcelCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParcelCategoryViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    inner class ParcelCategoryViewHolder(private val binding: ItemParcelTypeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: ParcelCategoryModel)  {
            binding.tvNaming.text = category.name
            binding.tvFact.text = category.factShpis.size.toString()
            binding.tvPlan.text = category.planShpis.size.toString()
        }
    }


}
