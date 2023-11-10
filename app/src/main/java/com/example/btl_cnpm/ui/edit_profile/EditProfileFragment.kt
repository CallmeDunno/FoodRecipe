package com.example.btl_cnpm.ui.edit_profile

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentEditProfileBinding
import com.example.btl_cnpm.model.User
import com.example.btl_cnpm.ui.profile.ProfileViewModel
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import com.example.btl_cnpm.utils.extensions.hide
import com.example.btl_cnpm.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FoodRecipeFragmentEditProfileBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_edit_profile

    private val editProfileViewModel by viewModels<EditProfileViewModel>()
    private var userId = ""
    private var id = ""
    private var uriImage = ""
    private var user = User()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                uriImage = uri.toString()
                Glide.with(requireContext()).load(uri).into(binding.imgProfileAvatar)
            }
        }

    @Inject
    lateinit var sharedPre: SharedPreferencesManager

    override fun initView() {
        super.initView()
        binding.apply {
            headerEditProfile.btnMoreHeader.visibility = View.GONE
            headerEditProfile.tvHeader.text = "Edit profile"
            userId = ""
        }
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            sharedPre.getString("idUserTemp")?.let {
                userId = it
            }

            sharedPre.getString("idUserRemember")?.let {
                userId = it
            }
            editProfileViewModel.getUserId(userId).observe(requireActivity()) { result ->
                when (result) {
                    is UIState.Success -> {
                        user = result.data.key
                        edtUsername.setText(result.data.key.username)
                        edtBio.setText(result.data.key.bio)
                        if(result.data.key.image.isNotEmpty()) {
                            Glide.with(requireContext()).load(result.data.key.image).into(imgProfileAvatar)
                        }
                        id = result.data.value
                    }

                    is UIState.Failure -> {
                        result.message?.let { mes ->
                            showDialogFail(mes)
                        }
                    }
                }
            }

            btnEditAvatar.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            btnCancel.setOnClickListener {
                popBackStack()
            }

            headerEditProfile.btnBackHeader.setOnClickListener {
                popBackStack()
            }

            btnEdit.setOnClickListener {
                notify("updating.....")
                val username = edtUsername.text.toString()
                val bio = edtBio.text.toString()
                if(username.isEmpty()) {
                    showDialogFail("Username cannot be empty!")
                    return@setOnClickListener
                }
                if(uriImage.isNotEmpty()) {
                    editProfileViewModel.uploadImage(Uri.parse(uriImage)).observe(requireActivity()) {result ->
                        when(result) {
                            is UIState.Success -> {
                                editProfileViewModel.updateUser(id, username = username, bio, result.data).observe(requireActivity()) {
                                    when(it) {
                                        is UIState.Success -> {
                                            requireView().findNavController().navigate(R.id.profileFragment)
                                        }
                                        is UIState.Failure -> {
                                            it.message?.let { mes ->
                                                showDialogFail(mes)
                                            }
                                        }
                                    }
                                }
                            }
                            is UIState.Failure -> {
                                showDialogFail("update fail")
                            }
                        }
                    }
                } else {
                    editProfileViewModel.updateUser(id, username = username, bio, user.image).observe(requireActivity()) {
                        when(it) {
                            is UIState.Success -> {
                                requireView().findNavController().navigate(R.id.profileFragment)
                            }
                            is UIState.Failure -> {
                                it.message?.let { mes ->
                                    showDialogFail(mes)
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}