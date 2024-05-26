package com.example.futurerealty
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class RealtyAdapter( private val realtyList:ArrayList<realityData> , private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<RealtyAdapter.MyViewHolder>()
{

    interface OnItemClickListener {
        fun onItemClick(realtyData: realityData)
    }




    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val titleImage:ShapeableImageView=itemView.findViewById(R.id.titleImage)
        val price:TextView=itemView.findViewById(R.id.textPrice)
        val details:TextView=itemView.findViewById(R.id.texthint)
        val realtyLocation:TextView=itemView.findViewById(R.id.textlocation)
        val whatsappBtn: Button = itemView.findViewById(R.id.whatsappBtn)
        val callUSBtn: Button = itemView.findViewById(R.id.callUSBtn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.realty_item, parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val currentItem = realtyList[position]
        Picasso.get().load(currentItem.imageUrl).into(holder.titleImage)
        holder.price.text = currentItem.price + " $"
        holder.details.text = currentItem.description
        holder.realtyLocation.text = currentItem.location

        holder.whatsappBtn.setOnClickListener {
            val phoneNumber = currentItem.contact
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                holder.itemView.context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(holder.itemView.context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
            }        }

        holder.callUSBtn.setOnClickListener {
            val phoneNumber = currentItem.contact
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            try {
                holder.itemView.context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(holder.itemView.context, "Cannot make a call", Toast.LENGTH_SHORT).show()
            }        }


        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(currentItem )
        }

    }


    override fun getItemCount(): Int
    {
        return realtyList.size
    }



}

