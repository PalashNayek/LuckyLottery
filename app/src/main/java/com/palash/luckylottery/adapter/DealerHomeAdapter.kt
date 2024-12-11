package com.palash.luckylottery.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.palash.luckylottery.R
import com.palash.luckylottery.models.LotteryHomeResponse
import kotlinx.coroutines.delay

class DealerHomeAdapter(private val onProductClicked: (LotteryHomeResponse) -> Unit) :
    ListAdapter<LotteryHomeResponse, DealerHomeAdapter.ProgrammingViewHolder>(DiffUtil()) {
    private var isMultiSelectMode = false // Tracks if multi-selection mode is active
    private val selectedItems = mutableSetOf<Int>() // Stores selected item IDs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lottery_item_view, parent, false)
        return ProgrammingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProgrammingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val mainLL = view.findViewById<LinearLayout>(R.id.ll_LotterView)
        private val ticketId = view.findViewById<TextView>(R.id.ticket_id)
        private val ticketNumber1 = view.findViewById<TextView>(R.id.lotteryTxt1)
        private val ticketNumber2 = view.findViewById<TextView>(R.id.lotteryTxt2)
        private val ticketNumber3 = view.findViewById<TextView>(R.id.lotteryTxt3)
        private val ticketNumber4 = view.findViewById<TextView>(R.id.lotteryTxt4)
        private val ticketNumber5 = view.findViewById<TextView>(R.id.lotteryTxt5)

        fun bind(item: LotteryHomeResponse) {
            val background = item.selling

            if (background) {
                //true
                mainLL.setBackgroundResource(R.drawable.selling_view)
                if (selectedItems.contains(item.ticketId)) Color.LTGRAY else Color.TRANSPARENT
                mainLL.setOnClickListener {
                    if (!isMultiSelectMode) {
                        isMultiSelectMode = true
                    }
                    toggleSelection(item)
                    true
                    //onProductClicked(item)
                }
            } else {
                //false
                mainLL.setBackgroundResource(R.drawable.selled_view)
            }
            ticketId.text = item.ticketId.toString()
            val lotteryNumber = item.ticketNumber
            var i: Int = 0
            val textViews = arrayOf(
                ticketNumber1,
                ticketNumber2,
                ticketNumber3,
                ticketNumber4,
                ticketNumber5
            )
            for (digit in lotteryNumber) {
                textViews[i].text = digit.toString()
                i++
            }
        }

        private fun toggleSelection(item: LotteryHomeResponse) {
            if (selectedItems.contains(item.ticketId)) {
                selectedItems.remove(item.ticketId)
            } else {
                selectedItems.add(item.ticketId)
            }
            notifyItemChanged(adapterPosition) // Update only the clicked item
            //onProductClicked(List<LotteryHomeResponse>)
                //onProductClicked(getSelectedItems())
        }

        private fun getSelectedItems(): List<LotteryHomeResponse> {
            return currentList.filter { selectedItems.contains(it.ticketId) }
        }
        // Get currently selected items
        /*fun getSelectedItems(): List<LotteryHomeResponse> {
            return currentList.filter { selectedItems.contains(it.ticketId) }
        }*/
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<LotteryHomeResponse>() {
        override fun areItemsTheSame(
            oldItem: LotteryHomeResponse,
            newItem: LotteryHomeResponse
        ): Boolean {
            return oldItem.ticketId == newItem.ticketId
        }

        override fun areContentsTheSame(
            oldItem: LotteryHomeResponse,
            newItem: LotteryHomeResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}