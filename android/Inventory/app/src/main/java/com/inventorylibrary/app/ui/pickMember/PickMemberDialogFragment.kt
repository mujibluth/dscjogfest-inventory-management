package com.inventorylibrary.app.ui.pickMember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.inventorylibrary.app.databinding.PickMemberDialogFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PickMemberDialogFragment : Fragment() {

    private val viewModel: PickMemberDialogViewModel by viewModel()
    private val args: PickMemberDialogFragmentArgs by navArgs()
    private var _binding: PickMemberDialogFragmentBinding? = null
    private val binding: PickMemberDialogFragmentBinding get() = _binding!!
    private lateinit var memberAdapter: PickMemberAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PickMemberDialogFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        memberAdapter = PickMemberAdapter(args.isCatalogOut)
        binding.memberRv.adapter = memberAdapter
        viewModel.memberList.observe(viewLifecycleOwner){ memberList ->
            memberAdapter.submitList(memberList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}