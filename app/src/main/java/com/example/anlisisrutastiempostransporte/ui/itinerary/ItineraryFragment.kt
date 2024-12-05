package com.example.anlisisrutastiempostransporte.ui.itinerary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotlottie.dlplayer.Mode
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.FragmentItineraryBinding
import com.example.anlisisrutastiempostransporte.di.StationComponent
import com.example.anlisisrutastiempostransporte.di.StationModule
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryAction
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryItemModel
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryModel
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryNavigation
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryResultModel
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryViewModel
import com.example.anlisisrutastiempostransporte.utils.app
import com.example.anlisisrutastiempostransporte.utils.getViewModel
import com.example.anlisisrutastiempostransporte.utils.setItemDecorationSpacing
import com.lottiefiles.dotlottie.core.model.Config
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import java.util.concurrent.TimeUnit

class ItineraryFragment : Fragment() {

    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var stationComponent: StationComponent
    private val viewModel: ItineraryViewModel by lazy {
        getViewModel { stationComponent.itineraryViewModel }
    }
    private lateinit var itineraryModel: ItineraryModel
    private lateinit var config: Config
    private lateinit var labelAdapter: ItineraryAdapter

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
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var aux = ""
        arguments?.let {
            itineraryModel = ItineraryFragmentArgs.fromBundle(it).itineraryModel
            itineraryModel.allStations.forEach { aux += it.startTerminalLength }
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ItineraryFragment_to_PanelFragment)
        }

        config = Config.Builder()
            .autoplay(true)
            .speed(1f)
            .loop(true)
            .source(DotLottieSource.Asset(TRAIN_VIEW))
            .useFrameInterpolation(true)
            .playMode(Mode.FORWARD)
            .build()

        labelAdapter = ItineraryAdapter(requireContext())
        binding.rv.run {
            layoutManager = LinearLayoutManager(requireContext())
            setItemDecorationSpacing(resources.getDimension(R.dimen.list_item_spacing_half))
            adapter = labelAdapter
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        viewModel.onEvent(Event(ItineraryAction.OnCalculate(itineraryModel = itineraryModel)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateEvents(event: Event<ItineraryNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is ItineraryNavigation.Itinerary -> navigation.run {
                    binding.lottieView.visibility = VISIBLE
                    binding.lottieView.load(config)
                    labelAdapter.updateData(getItems(results))
                }
                ItineraryNavigation.ErrorData -> TODO()
            }
        }
    }

    private fun getItems(results: ItineraryResultModel): List<ItineraryItemModel> {
        return listOf(
            ItineraryItemModel(
                R.drawable.ic_start_terminal,
                getString(R.string.estaci_n_inicial),
                results.startStation.name
            ),
            ItineraryItemModel(
                R.drawable.ic_end_terminal,
                getString(R.string.estaci_n_final),
                results.endStation.name
            ),
            ItineraryItemModel(
                R.drawable.ic_ruler,
                getString(R.string.distancia_recorrida),
                results.distance.toString() + " $KM"
            ),
            ItineraryItemModel(
                R.drawable.ic_train,
                getString(R.string.n_mero_de_estaciones_recorridas),
                results.numberStations.toString()
            ),
            ItineraryItemModel(
                R.drawable.ic_payment,
                getString(R.string.costo_total),
                "$${results.priceTotal} $CURRENCY"
            ),
            ItineraryItemModel(
                R.drawable.ic_time,
                getString(R.string.tiempo_considerando_tiempos_de_espera),
                formatearMinutosAHoraMinuto(results.tripTimeTotal.toLong())
            ),
            ItineraryItemModel(
                R.drawable.ic_speed,
                getString(R.string.velocidad_del_tren),
                results.speed.toString() + " $KMH"
            ),
        )
    }

    private fun formatearMinutosAHoraMinuto(minutos: Long): String {
        val hours: Long = TimeUnit.MINUTES.toHours(minutos)
        val minuts: Long =
            TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MINUTES.toHours(
                    minutos
                )
            )
        return String.format(HOUR_FORMAT, hours, minuts)
    }

    companion object {

        private const val TRAIN_VIEW = "mitrain.json"
        private const val HOUR_FORMAT = "%02d:%02d"
        private const val KMH = "KM/H"
        private const val KM = "KM"
        private const val CURRENCY = "MXN"
    }
}