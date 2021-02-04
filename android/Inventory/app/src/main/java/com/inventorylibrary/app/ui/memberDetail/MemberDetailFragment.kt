package com.inventorylibrary.app.ui.memberDetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.inventorylibrary.app.databinding.MemberDetailFragmentBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MemberDetailFragment : Fragment() {

    private val viewModel: MemberDetailViewModel by viewModel()
    private val args: MemberDetailFragmentArgs by navArgs()
    private var _binding: MemberDetailFragmentBinding? = null
    private val binding: MemberDetailFragmentBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MemberDetailFragmentBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO){
            try {

                val member = viewModel.getMemberById(args.memberID) ?: return@launch
                withContext(Dispatchers.Main){
                    with(binding){
                        member.profilePicture?.let {
                            Picasso.get().load(Uri.parse(it)).into(profileImage)
                        }
                        member.identityPicture?.let {
                            Picasso.get().load(Uri.parse(it)).into(identityImage)
                        }
                        memberName.text = member.name
                        memberID.text = member.memberID.toString()
                        memberAddress.text = member.address
                        birthPlace.text = member.birthPlace
                        sex.text = if (member.sex == 0) "Perempuan" else "Laki-laki"
                        job.text = member.job
                        phoneNumber.text = member.phone
                        email.text = member.email
                        memberDate.text = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date(member.joinDate))
                    }
                }
            }catch (e: Exception){}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}