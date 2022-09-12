package com.example.covidtracker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView


class StateRVAdapter(private val stateList:List<StateModal>):
    RecyclerView.Adapter<StateRVAdapter.StatRVViewHolder>() {

    class StatRVViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val stateNameTV:TextView= itemView.findViewById(R.id.idRvState)
        val casesTV:TextView=itemView.findViewById(R.id.idTVCase)
        val deathsTV:TextView=itemView.findViewById(R.id.idDeaths)
        val  RecoveredTV:TextView=itemView.findViewById(R.id.idRecd)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StatRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StatRVViewHolder, position: Int) {
        val stateData =stateList[position]
        holder.casesTV.text=stateData.cases.toString()
        holder.stateNameTV.text=stateData.state
        holder.deathsTV.text=stateData.deaths.toString()
        holder.RecoveredTV.text=stateData.recovered.toString()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}