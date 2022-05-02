package com.example.ladm_u3_p1_sqlite.ui.dashboard

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ladm_u3_p1_sqlite.Area
import com.example.ladm_u3_p1_sqlite.Subdepartamento
import com.example.ladm_u3_p1_sqlite.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    var vectorIDArea = ArrayList<Int>()
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnDescrip.setOnClickListener {
            mostrarDatosDescripArea()
        }
        binding.btnDivision.setOnClickListener {
            mostrarDatosDivArea()
        }
        binding.btnidEdficio.setOnClickListener {
            mostrarDatosEdifID()
        }
        binding.btnDecArea.setOnClickListener {
            mostrarDatosDescripArea()
        }
        binding.btnDivArea.setOnClickListener {
            mostrarDatosDivArea()
        }
        return root
    }
    fun mostrarDatosEdifID(){
        var listaDatos = Subdepartamento(this.requireContext()).mostrartblSub()
        var arrayDatos = ArrayList<String>()
        vectorIDArea.clear()
        (0..listaDatos.size-1).forEach {
            val ar = listaDatos.get(it)
            arrayDatos.add("ID EDFICIO:"+ar.idEdificio)
            vectorIDArea.add(ar.idSubdepto)
        }
        binding.listaBusqueda.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1, arrayDatos)
    }
    fun mostrarDatosDescripArea(){
        var listaAreas = Area(this.requireContext()).mostrartblArea()
        var descripArea = ArrayList<String>()
        vectorIDArea.clear()
        (0..listaAreas.size-1).forEach {
            val ar = listaAreas.get(it)
            descripArea.add("IDAREA: "+ar.idArea+" DESCRIP:"+ar.descrip)
            vectorIDArea.add(ar.idArea)
        }
        binding.listaBusqueda.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1, descripArea)
    }
    fun mostrarDatosDivArea(){
        var listaAreas = Area(this.requireContext()).mostrartblArea()
        var descripArea = ArrayList<String>()
        vectorIDArea.clear()
        (0..listaAreas.size-1).forEach {
            val ar = listaAreas.get(it)
            descripArea.add("IDAREA: "+ar.idArea+" DIVIS:"+ar.divis)
            vectorIDArea.add(ar.idArea)
        }
        binding.listaBusqueda.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1, descripArea)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}