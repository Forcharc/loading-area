package kz.kazpost.loadingarea.ui._adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow.MATCH_PARENT
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.databinding.ItemTransportBinding
import kz.kazpost.loadingarea.ui._models.TransportModel
import kz.kazpost.loadingarea.ui._models.WorkerState

class TransportAdapter(
    private val transportActionListener: TransportActionListener
) :
    PagingDataAdapter<TransportModel, TransportAdapter.TransportViewHolder>(TransportModel.TransportItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTransportBinding.inflate(layoutInflater, parent, false)
        return TransportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        val item = getItem(position) ?: TransportModel(
            -1,
            "",
            "",
            "",
            "",
            emptyList(),
            0,
            WorkerState.NO_WORKER
        )
        holder.bind(item)
    }

    inner class TransportViewHolder(private val binding: ItemTransportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        private var currentTInvoice: TransportModel? = null

        init {
            binding.bOperations.setOnClickListener { buttonView ->
                showOperationsPopup(buttonView)
            }
        }

        fun bind(transportModel: TransportModel) {
            currentTInvoice = transportModel

            val color: Int = ResourcesCompat.getColor(
                context.resources,
                when (transportModel.workerState) {
                    WorkerState.NO_WORKER -> R.color.white
                    WorkerState.RIGHT_WORKER -> android.R.color.holo_green_light
                    WorkerState.WRONG_WORKER -> android.R.color.holo_red_light
                },
                null
            )
            binding.card.setCardBackgroundColor(color)

            binding.tvTInvoiceNumber.text = transportModel.tInvoiceNumber
            binding.tvPassage.text =
                context.getString(R.string.passage, transportModel.from, transportModel.to)
            binding.tvType.text = transportModel.transportType
        }

        private fun showOperationsPopup(anchor: View) {
            val popupWindow = ListPopupWindow(context)
            val popupAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                context.resources.getStringArray(R.array.operations)
            )
            popupWindow.setOnItemClickListener { _, _, position, _ ->
                currentTInvoice?.let { tInvoice ->
                    when (position) {
                        0 -> {
                            transportActionListener.onTransportAction(
                                tInvoice,
                                TransportActionType.ADD_S_INVOICE
                            )
                        }
                        1 -> {
                            transportActionListener.onTransportAction(
                                tInvoice,
                                TransportActionType.LOAD_TRANSPORT
                            )
                        }
/*
                        1 -> {
                            transportActionListener.onTransportAction(
                                tInvoice,
                                TransportActionType.REMOVE_S_INVOICE
                            )
                        }
*/
                    }
                }
                popupWindow.dismiss()
            }
            popupWindow.anchorView = anchor
            popupWindow.width = MATCH_PARENT
            popupWindow.setAdapter(popupAdapter)
            popupWindow.show()
        }
    }

    interface TransportActionListener {
        fun onTransportAction(transportModel: TransportModel, actionType: TransportActionType)
    }

    enum class TransportActionType {
        ADD_S_INVOICE,
        REMOVE_S_INVOICE,
        LOAD_TRANSPORT
    }


}