package com.example.covid_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class stateRVadaptor(private val stateList:List<StateModal>) :
    RecyclerView.Adapter<stateRVadaptor.StateRVviewHolder>() {
    class StateRVviewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val StateNameTV:TextView = itemView.findViewById(R.id.idtvstate)
        val casesTv:TextView = itemView.findViewById(R.id.idTVcases)
        val recoveredTV:TextView = itemView.findViewById(R.id.idTVrecovered)
        val deathsTV:TextView = itemView.findViewById(R.id.idTVDeaths)
        

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVviewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRVviewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateRVviewHolder, position: Int) {
        val stateData = stateList[position]
        holder.casesTv.text = stateData.cases.toString()
        holder.StateNameTV.text = stateData.state
        holder.deathsTV.text = stateData.deaths.toString()
        holder.recoveredTV.text = stateData.recovered.toString()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}