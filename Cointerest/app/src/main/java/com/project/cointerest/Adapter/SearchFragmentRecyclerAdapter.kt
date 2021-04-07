package com.project.cointerest.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.project.cointerest.CoinData
import com.project.cointerest.Fragment.searchFragment
//import com.project.cointerest.Fragment.CoinList
import com.project.cointerest.R
import kotlinx.android.synthetic.main.fragment_search.view.*



class SearchFragmentRecyclerAdapter(val context: Context, var coin_list:ArrayList<CoinData>) :
        RecyclerView.Adapter<SearchFragmentRecyclerAdapter.Holder>(), Filterable {

    val unFilteredList = coin_list // 필터 전
    var filteredList = coin_list // 필터 후

    //선택한 아이템리스트
    var selectedList = ArrayList<CoinData>()

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener


    override fun getItemCount(): Int = filteredList.size
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString = constraint.toString()
                println(charString)
                filteredList = if (charString.isEmpty()) {
                    println("비엇음")
                    unFilteredList

                } else {
                    var filteringList = ArrayList<CoinData>()
                    for (item in unFilteredList) {
                        if (item.kor_name == charString ||
                                item.symbol.toLowerCase() == charString.toLowerCase()) {
                            filteringList.add(item)
                            println("찾았다!")
                            println(item.kor_name)
                        }
                    }
                    filteringList

                }
                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults??) {
                filteredList = results?.values as ArrayList<CoinData>
                println("result")
                println(filteredList.size)

                //이거했는데 수정반영이 안됨
                notifyDataSetChanged()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_1, parent, false)
        return Holder(view)
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val C_image = itemView?.findViewById<ImageView>(R.id.imageView2)
        val C_kor = itemView?.findViewById<TextView>(R.id.Coin_kor)
        val C_symbol = itemView?.findViewById<TextView>(R.id.Symbol)
        val C_market = itemView?.findViewById<TextView>(R.id.Market)

        fun bind(coin: CoinData, context: Context) {

            if (coin.coin_image != "") {
                val resourceId = context.resources.getIdentifier(coin.coin_image, "drawable", context.packageName)
                C_image?.setImageResource(resourceId)
            } else {
                C_image?.setImageResource(R.mipmap.ic_launcher)
            }
            C_kor?.text = coin.kor_name
            C_symbol?.text = coin.symbol
            C_market?.text = coin.market

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        //여기에는 데이터셋 수정이 적용 안됐음
        println("check@@@!!")
        println(filteredList.size)

        holder?.bind(filteredList[position], context)

        holder.itemView.setOnClickListener {
            var duplicateCheck = 0
            //println("클릭 체크메이드")

            println("${filteredList[position]} 선택")

            var idx = 0

            //원인은 삭제로직에 있는거같음(해결)
            for(coin in selectedList){
                if((filteredList[position].kor_name == coin.kor_name)
                        && (filteredList[position].market == coin.market)){

                    selectedList.remove(selectedList[idx])

                    //println("${idx}번째 인덱스")
                    println("${filteredList[position].kor_name} 선택해제")

                    println("##선택된 코인들###")
                    for(Ncoin in selectedList){
                        println("${Ncoin.kor_name} - ${Ncoin.market}")
                    }
                    println("#################")

                    duplicateCheck = 1
                    break
                }
                idx++
            }

            //리스트 중간에 삭제됐던거 다시 추가하면 튕김
            if(duplicateCheck == 0){
                println("추가!!")
                selectedList.add(filteredList[position])
            }


            println("##선택된 코인들###")
            for(coin in selectedList){
                println("${coin.kor_name} - ${coin.market}")
            }
            println("#################")


        }

    }



}