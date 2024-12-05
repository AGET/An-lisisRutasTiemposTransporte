package com.example.anlisisrutastiempostransporte.ui.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.anlisisrutastiempostransporte.databinding.DialogCustomViewBinding

class CustomDialogFragment : DialogFragment() {

    private var _binding: DialogCustomViewBinding? = null
    private val binding: DialogCustomViewBinding get() = _binding!!
    private var title = ""
    private var message = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomViewBinding.inflate(inflater, container, false)
        arguments?.let {
            title = it.getString(EXTRA_TITLE, "")
            message = it.getString(EXTRA_MESSAGE, "")
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNegative.visibility = GONE
        binding.tvTitle.text = title
        binding.tvMessage.text = message
        binding.buttonPositive.setOnClickListener { dismiss() }
        binding.buttonNegative.setOnClickListener { dismiss() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                super.onBackPressed()
            }
        }
    }

    companion object {
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        fun newInstance(title: String, message: String): CustomDialogFragment {
            val fragment = CustomDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_TITLE, title)
                putString(EXTRA_MESSAGE, message)
            }
            return fragment
        }
    }
}
