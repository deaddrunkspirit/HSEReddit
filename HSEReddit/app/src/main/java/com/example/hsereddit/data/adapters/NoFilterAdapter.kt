package com.example.hsereddit.data.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull

class NoFilterAdapter<T>(
    @NonNull context: Context,
    @LayoutRes resource: Int,
    @NonNull val items: ArrayList<T>
) : ArrayAdapter<T>(context, resource, items) {

    override fun getFilter(): Filter {
        return DisabledFilter()
    }

    fun addItems(coll: Collection<T>) {
        items.addAll(coll)
    }

    private inner class DisabledFilter : Filter() {
        override fun performFiltering(arg0: CharSequence): FilterResults {
            val result = FilterResults()
            result.values = items
            result.count = items.size
            return result
        }
        override fun publishResults(arg0: CharSequence, arg1: FilterResults) {
            notifyDataSetChanged()
        }
    }

}