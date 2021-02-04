package com.inventorylibrary.app.ui.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventorylibrary.app.MobileNavigationDirections
import com.inventorylibrary.app.databinding.ItemMemberBinding
import com.inventorylibrary.app.entity.MemberEntity
import com.squareup.picasso.Picasso

class MemberAdapter: ListAdapter<MemberEntity, MemberAdapter.MemberViewHolder>(
    object : DiffUtil.ItemCallback<MemberEntity>(){
        override fun areItemsTheSame(oldItem: MemberEntity, newItem: MemberEntity): Boolean {
            return oldItem.memberID == newItem.memberID
        }

        override fun areContentsTheSame(oldItem: MemberEntity, newItem: MemberEntity): Boolean {
            return oldItem == newItem
        }
    }

) {

    inner class MemberViewHolder(private val binding: ItemMemberBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(member: MemberEntity){
            with(binding){
                memberName.text = member.name
                memberID.text = member.memberID.toString()
                memberAddress.text = member.address

                member.profilePicture?.let {
                    Picasso.get().load(it).into(memberImage)
                }
                root.setOnClickListener {
                    val direction = MobileNavigationDirections.actionGlobalMemberDetailFragment(member.memberID!!)
                    it.findNavController().navigate(direction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}