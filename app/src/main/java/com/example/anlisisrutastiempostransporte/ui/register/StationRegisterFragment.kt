package com.example.anlisisrutastiempostransporte.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.FragmentStationRegisterBinding
import com.example.anlisisrutastiempostransporte.di.StationComponent
import com.example.anlisisrutastiempostransporte.di.StationModule
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterAction
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterNavigation
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterViewModel
import com.example.anlisisrutastiempostransporte.ui.utils.CustomDialogFragment
import com.example.anlisisrutastiempostransporte.utils.app
import com.example.anlisisrutastiempostransporte.utils.getViewModel
import com.example.anlisisrutastiempostransporte.utils.showDialogFragment

class StationRegisterFragment : Fragment() {
    private var _binding: FragmentStationRegisterBinding? = null
    private val binding: FragmentStationRegisterBinding get() = _binding!!

    private lateinit var stationComponent: StationComponent
    private val viewModel: StationRegisterViewModel by lazy {
        getViewModel { stationComponent.stationRegisterViewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stationComponent = requireContext().app.component.inject(StationModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentStationRegisterBinding>(
            inflater, R.layout.fragment_station_register, container, false
        ).apply { lifecycleOwner = this@StationRegisterFragment }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSave.setOnClickListener {
                viewModel.onEvent(
                    Event(
                        StationRegisterAction.OnValidRegister(
                            Station(
                                name = tietName.text.toString(),
                                startTerminalLength = tietDistance.text.toString().toDoubleOrNull()
                                    ?: 0.0
                            )
                        )
                    )
                )
            }
        }
        viewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
        viewModel.onEvent(Event(StationRegisterAction.GetAllStations))
    }


    private fun validateEvents(event: Event<StationRegisterNavigation>?) {
        event?.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                StationRegisterNavigation.ShowLoading -> {
                    binding.loading.visibility = VISIBLE
                }

                StationRegisterNavigation.HiddenLoading -> {
                    binding.loading.visibility = GONE
                }

                StationRegisterNavigation.SuccessRegister -> {
                    view?.let {
                        showDialogFragment(
                            CustomDialogFragment.newInstance(
                                "Rutas",
                                "Registrado!!"
                            )
                        )
                    }
                }

                StationRegisterNavigation.ErrorRegister -> {}
                is StationRegisterNavigation.ShowAllStations -> navigation.run {
                    stations.forEach {
                        Log.v(
                            "TAG",
                            "id:${it.id} nombre:${it.name} distancia: ${it.startTerminalLength}"
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = StationRegisterFragment()
    }

}
