package com.adnet.testgit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adnet.testgit.dragDrop.DragDropAdapter
import com.adnet.testgit.dragDrop.DragDropViewHolder
import kotlinx.android.synthetic.main.item_view.view.*

class NamesAdapter(private var item: List<String>) :
    RecyclerView.Adapter<NamesAdapter.ViewHolder>(),
    DragDropAdapter {

    lateinit var itemTouchHelper: ItemTouchHelper

    var dataList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            viewGroup.context
        ).inflate(R.layout.item_view, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(item, position)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), DragDropViewHolder {

        fun bindData(item: List<String>, position: Int) {
            itemView.dataTextView.text = item[position]

            itemView.setOnLongClickListener {
                this@ViewHolder.onViewHolderStartDrag()
                itemTouchHelper.startDrag(this@ViewHolder)
                true
            }
        }

        override fun onViewHolderStartDrag() {
            (itemView as CardView).setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.cardDragColor
                )
            )
        }

        override fun onViewHolderDropped() {
            // Change item background color
            (itemView as CardView).setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.cardBgColor
                )
            )
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataList[fromPosition] = dataList[toPosition].also {
            dataList[toPosition] = dataList[fromPosition]
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDropped(
        fromPosition: Int,
        toPosition: Int,
        viewHolder: RecyclerView.ViewHolder
    ) {
        if (fromPosition != toPosition) {
            item = dataList
        }

        (viewHolder as ViewHolder).onViewHolderDropped()
    }
}