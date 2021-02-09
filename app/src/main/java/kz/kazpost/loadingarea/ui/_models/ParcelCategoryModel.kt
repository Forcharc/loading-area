package kz.kazpost.loadingarea.ui._models

import androidx.recyclerview.widget.DiffUtil

data class ParcelCategoryModel(
    val name: String,
    val planShpis: MutableList<String>,
    var factShpis: List<String>
) {
    class ItemCallback : DiffUtil.ItemCallback<ParcelCategoryModel>() {
        override fun areItemsTheSame(
            oldItem: ParcelCategoryModel,
            newItem: ParcelCategoryModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ParcelCategoryModel,
            newItem: ParcelCategoryModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}
