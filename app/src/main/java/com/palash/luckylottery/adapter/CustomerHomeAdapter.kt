package com.palash.luckylottery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.palash.luckylottery.R
import com.palash.luckylottery.models.LotteryHomeResponse

class CustomerHomeAdapter : ListAdapter<LotteryHomeResponse, CustomerHomeAdapter.ProgrammingViewHolder>(DiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lottery_item_view, parent, false)
        return ProgrammingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgrammingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ProgrammingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val mainLL = view.findViewById<LinearLayout>(R.id.ll_LotterView)
        private val ticketId = view.findViewById<TextView>(R.id.ticket_id)
        private val ticketNumber1 = view.findViewById<TextView>(R.id.lotteryTxt1)
        private val ticketNumber2 = view.findViewById<TextView>(R.id.lotteryTxt2)
        private val ticketNumber3 = view.findViewById<TextView>(R.id.lotteryTxt3)
        private val ticketNumber4 = view.findViewById<TextView>(R.id.lotteryTxt4)
        private val ticketNumber5 = view.findViewById<TextView>(R.id.lotteryTxt5)

        fun bind(item: LotteryHomeResponse) {
            val context = itemView.context
            val background = item.selling
            if (background){
                //true
                //val color = ContextCompat.getDrawable(context, R.drawable.half_white_background)
                mainLL.setBackgroundResource(R.drawable.selling_view)
            }else{
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
            //initial.text = item.initial
        }
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