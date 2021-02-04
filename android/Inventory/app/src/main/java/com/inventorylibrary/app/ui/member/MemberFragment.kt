package com.inventorylibrary.app.ui.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.inventorylibrary.app.R
import com.inventorylibrary.app.databinding.FragmentMemberBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemberFragment : Fragment() {

    private val viewModel: MemberViewModel by viewModel()
    private var _binding: FragmentMemberBinding? = null
    private val binding: FragmentMemberBinding get() = _binding!!
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemberBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberAdapter = MemberAdapter()
        binding.memberRv.adapter = memberAdapter
        binding.addMemberButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_nav_member_to_addMemberFragment)
        )
        viewModel.memberList.observe(viewLifecycleOwner){ memberList ->
            memberAdapter.submitList(memberList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}