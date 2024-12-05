package com.example.anlisisrutastiempostransporte.ui.liststations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anlisisrutastiempostransporte.MainActivity
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.FragmentListStationsBinding
import com.example.anlisisrutastiempostransporte.di.StationComponent
import com.example.anlisisrutastiempostransporte.di.StationModule
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsAction
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsNavigation
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsViewModel
import com.example.anlisisrutastiempostransporte.utils.app
import com.example.anlisisrutastiempostransporte.utils.getViewModel
import com.example.anlisisrutastiempostransporte.utils.goneWithAnimation
import com.example.anlisisrutastiempostransporte.utils.setItemDecorationSpacing
import com.example.anlisisrutastiempostransporte.utils.visibleWithAnimation

class ListStationsFragment : Fragment() {

    private var _binding: FragmentListStationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var stationAdapter: StationsAdapter

    private lateinit var stationComponent: StationComponent
    private val viewModel: ListStationsViewModel by lazy {
        getViewModel { stationComponent.listStationsViewModel }
    }
    val listenerBack: View.OnClickListener =
        View.OnClickListener { findNavController().navigate(R.id.action_ListStationsFragment_to_PanelFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stationComponent = requireContext().app.component.inject(StationModule())
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_ItineraryFragment_to_PanelFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentListStationsBinding>(
            inflater,
            R.layout.fragment_list_stations,
            container,
            false
        ).apply {
            lifecycleOwner = this@ListStationsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener(listenerBack)

        stationAdapter = StationsAdapter()

        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        binding.rv.run {
            setItemDecorationSpacing(resources.getDimension(R.dimen.list_item_padding))
            adapter = stationAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                val VALUE_FOR_MOTION = 100
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > VALUE_FOR_MOTION && binding.tvTitle.isVisible) {
                        hiddeComponents()
                    }

                    if (dy < -VALUE_FOR_MOTION && binding.tvTitle.isVisible.not()) {
                        showComponents()
                    }

                    if (!recyclerView.canScrollVertically(-1) && binding.tvTitle.isVisible.not()) {
                        showComponents()
                    }
                }
            })
        }

        viewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        viewModel.onEvent(Event(ListStationsAction.GetAllStations))
    }

    private fun showComponents() {
        binding.tvTitle.visibility = VISIBLE
        binding.buttonBack.visibleWithAnimation()
        binding.buttonBack.setOnClickListener(listenerBack)
        (requireActivity() as MainActivity).supportActionBar?.show()
    }

    private fun hiddeComponents() {
        binding.tvTitle.visibility = GONE
        binding.buttonBack.goneWithAnimation()
        (requireActivity() as MainActivity).supportActionBar?.hide()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateEvents(event: Event<ListStationsNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                ListStationsNavigation.HiddenLoading -> binding.loading.visibility = GONE
                is ListStationsNavigation.ShowAllStations -> navigation.run {
                    stationAdapter.updateData(stations)
                }

                ListStationsNavigation.ShowLoading -> binding.loading.visibility = VISIBLE
            }
        }
    }
}
