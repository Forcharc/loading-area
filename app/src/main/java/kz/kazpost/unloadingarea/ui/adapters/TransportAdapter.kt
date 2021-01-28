package kz.kazpost.unloadingarea.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import kz.kazpost.unloadingarea.R
import kz.kazpost.unloadingarea.databinding.ItemTransportBinding
import kz.kazpost.unloadingarea.data.models.TransportModel
import kz.kazpost.unloadingarea.util.showSnackShort

class TransportAdapter : RecyclerView.Adapter<TransportAdapter.TransportViewHolder>() {
    private val listDiffer = AsyncListDiffer(this, TransportItemCallback())

    fun submitList(list: List<TransportModel>) {
        listDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTransportBinding.inflate(layoutInflater, parent, false)
        return TransportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    inner class TransportViewHolder(private val binding: ItemTransportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        init {
            binding.bOperations.setOnClickListener { buttonView ->
                showOperationsPopup(buttonView)
            }
        }

        fun bind(transportModel: TransportModel) {
            binding.tvTInvoiceNumber.text = transportModel.tInvoiceNumber
            binding.tvPassage.text =
                context.getString(R.string.passage, transportModel.from, transportModel.to)
            binding.tvType.text = transportModel.transportType
        }

        private fun showOperationsPopup(anchor: View) {
            val popupWindow = ListPopupWindow(context)
            val popupAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                context.resources.getStringArray(R.array.operations)
            )
            popupWindow.setOnItemClickListener { _, _, position, _ ->
                when (position) {
                    0 -> {
                        anchor.showSnackShort("add s")
                    }
                    1 -> {
                        anchor.showSnackShort("remove s")
                    }
                    2 -> {
                        anchor.showSnackShort("load")
                    }
                }
            }
            popupWindow.anchorView = anchor
            popupWindow.setAdapter(popupAdapter)
            popupWindow.show()
        }
    }

    private inner class TransportItemCallback : ItemCallback<TransportModel>() {
        override fun areItemsTheSame(oldItem: TransportModel, newItem: TransportModel): Boolean {
            return oldItem.tInvoiceNumber == newItem.tInvoiceNumber
        }

        override fun areContentsTheSame(oldItem: TransportModel, newItem: TransportModel): Boolean {
            return oldItem == newItem
        }
    }
}