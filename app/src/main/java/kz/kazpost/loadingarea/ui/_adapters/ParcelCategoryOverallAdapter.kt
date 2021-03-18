package kz.kazpost.loadingarea.ui._adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kazpost.loadingarea.databinding.ItemParcelTypeOverallBinding
import kz.kazpost.loadingarea.ui._models.ParcelTypeOverallModel

class ParcelCategoryOverallAdapter :
    RecyclerView.Adapter<ParcelCategoryOverallAdapter.ParcelCategoryOverallViewHolder>() {


    private var model: ParcelTypeOverallModel? = null

    fun setModel(newModel: ParcelTypeOverallModel?) {
        if (this.model == null) {
            this.model = newModel
            if (newModel != null) {
                notifyItemInserted(0)
            }
        } else {
            this.model = newModel
            if (newModel != null) {
                notifyItemChanged(0)
            } else {
                notifyItemRemoved(0)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParcelCategoryOverallViewHolder {
        val context = parent.context
        val binding =
            ItemParcelTypeOverallBinding.inflate(LayoutInflater.from(context), parent, false)
        return ParcelCategoryOverallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParcelCategoryOverallViewHolder, position: Int) {
        model?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return if (model != null) 1 else 0
    }

    inner class ParcelCategoryOverallViewHolder(private val binding: ItemParcelTypeOverallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(parcelTypeModel: ParcelTypeOverallModel) {
            binding.tvPlan.text = parcelTypeModel.overallPlan.toString()
            binding.tvFact.text = parcelTypeModel.overallFact.toString()
        }

    }
}