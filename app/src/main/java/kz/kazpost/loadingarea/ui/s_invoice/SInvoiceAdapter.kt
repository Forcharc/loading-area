package kz.kazpost.loadingarea.ui.s_invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.databinding.ItemSInvoiceBinding

class SInvoiceAdapter : RecyclerView.Adapter<SInvoiceAdapter.SInvoiceViewHolder>() {
    private val listDiffer = AsyncListDiffer(this, SInvoiceCallback())

    private val selectedSInvoices = HashMap<SInvoiceModel, Boolean>()

    fun submitList(list: List<SInvoiceModel>) {
        listDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SInvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSInvoiceBinding.inflate(layoutInflater, parent, false)
        return SInvoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SInvoiceViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun getCheckedItems(): List<SInvoiceModel> {
        return selectedSInvoices.filter {
            it.value
        }.map { it.key }
    }

    inner class SInvoiceViewHolder(private val binding: ItemSInvoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(sInvoiceModel: SInvoiceModel) {
            binding.llTableRow.setOnClickListener {
                selectedSInvoices[sInvoiceModel] =
                    !(selectedSInvoices[sInvoiceModel] ?: false)
                notifyItemChanged(adapterPosition)
            }

            val colorResourceId =
                if (selectedSInvoices[sInvoiceModel] == true) {
                    android.R.color.holo_green_light
                } else {
                    R.color.white
                }

            binding.llTableRow.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    colorResourceId
                )
            )

            binding.tvNumber.text = sInvoiceModel.number
            binding.tvDestination.text = sInvoiceModel.destination
            binding.tvBagCount.text = sInvoiceModel.bagCount
            binding.tvParcelCount.text = sInvoiceModel.parcelCount
        }
    }

    class SInvoiceCallback : DiffUtil.ItemCallback<SInvoiceModel>() {
        override fun areItemsTheSame(oldItem: SInvoiceModel, newItem: SInvoiceModel): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: SInvoiceModel, newItem: SInvoiceModel): Boolean {
            return oldItem == newItem
        }

    }

}