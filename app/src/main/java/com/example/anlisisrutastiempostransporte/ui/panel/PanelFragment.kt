package com.example.anlisisrutastiempostransporte.ui.panel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.FragmentPanelBinding
import com.example.anlisisrutastiempostransporte.di.StationComponent
import com.example.anlisisrutastiempostransporte.di.StationModule
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryModel
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelAction
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelNavigation
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelViewModel
import com.example.anlisisrutastiempostransporte.utils.app
import com.example.anlisisrutastiempostransporte.utils.getViewModel

class PanelFragment : Fragment() {

    private var _binding: FragmentPanelBinding? = null
    private val binding: FragmentPanelBinding get() = _binding!!
    private lateinit var stationComponent: StationComponent
    private val viewModel: PanelViewModel by lazy {
        getViewModel { stationComponent.panelViewModel }
    }
    private var starStation: Station? = null
    private var endStation: Station? = null
    private var stations: List<Station>? = null

    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stationComponent = requireContext().app.component.inject(StationModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentPanelBinding>(
            inflater,
            R.layout.fragment_panel,
            container,
            false
        ).apply {
            lifecycleOwner = this@PanelFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonCalculate.setOnClickListener {
            val model = ItineraryModel(
                price = binding.edtPrice.text.toString().toDoubleOrNull() ?: 0.0,
                speed = binding.edtSpeed.text.toString().toIntOrNull() ?: 0,
                averageWaitingTime = binding.edtAverageWaitingTime.text.toString().toIntOrNull()
                    ?: 0,
                startStation = starStation ?: Station(0, "", 0.0),
                endStation = endStation ?: Station(0, "", 0.0),
                allStations = stations ?: emptyList()
            )
            val action = PanelFragmentDirections.actionPanelFragmentToItineraryFragment(model)
            findNavController().navigate(action)
        }

        binding.buttonShowStations.setOnClickListener {
            findNavController().navigate(R.id.action_PanelFragment_to_ListStationsFragment)
        }

        adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown)
        (binding.tilStartStation.editText as? AutoCompleteTextView)?.run {
            setAdapter(this@PanelFragment.adapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ -> starStation = stations?.get(position) }
        }
        (binding.tilEndStation.editText as? AutoCompleteTextView)?.run {
            setAdapter(this@PanelFragment.adapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ -> endStation = stations?.get(position) }

        }

        viewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        viewModel.onEvent(Event(PanelAction.ValidateFirstLaunched))
        viewModel.onEvent(Event(PanelAction.GetAllStations))

        /*        binding.lottieView.playAnimation()
        // Cuantas veces se reproducirá la animación (en este caso infinitas veces)
                binding.lottieView.repeatCount =  LottieDrawable.INFINITE
        // Establecer la velocidad de reproducción
                binding.lottieView.speed = 1.0f
        // Establecer el punto de inicio de la animación
                binding.lottieView.progress = .5f
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateEvents(event: Event<PanelNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                PanelNavigation.HiddenLoading -> binding.loading.visibility = GONE
                is PanelNavigation.ShowAllStations -> navigation.run {
                    this@PanelFragment.stations = stations
                    adapter.clear()
                    adapter.addAll(stations.map { it.name })
                    adapter.notifyDataSetChanged()
                }

                PanelNavigation.ShowLoading -> binding.loading.visibility = VISIBLE
            }
        }
    }
}
