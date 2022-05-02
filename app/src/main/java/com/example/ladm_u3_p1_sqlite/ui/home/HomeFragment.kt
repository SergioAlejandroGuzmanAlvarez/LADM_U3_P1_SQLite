package com.example.ladm_u3_p1_sqlite.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ladm_u3_p1_sqlite.Area
import com.example.ladm_u3_p1_sqlite.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var vectorIDArea = ArrayList<Int>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatos()
        binding.btnInsertar.setOnClickListener{
            var area = Area(this.requireContext())
            area.descrip = binding.txtDescrip.text.toString()
            area.divis = binding.txtDivis.text.toString()
            area.cant_emp = binding.txtCantidad.text.toString().toInt()
            val result = area.insertar()
            if(result){
                AlertDialog.Builder(this.requireContext())
                    .setTitle("[AREA INSERTADA]")
                    .setMessage("Se inserto una nueva area a la BD.")
                    .show()
                mostrarDatos()
                limpiarCampos()
            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("[ERROR]")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }
        binding.listaHome.setOnItemClickListener { adapterView, view, i, l ->
            val idDArea = vectorIDArea.get(i)
            val idGuardado = Area(this.requireContext()).mostrarSoloID(idDArea)
            AlertDialog.Builder(this.requireContext())
                .setTitle("¿Que deseas hacer?")
                .setMessage("Seleccionaste el ${idGuardado.idArea}"+" del area: ${idGuardado.descrip}\n Cargar los datos ayuda a corregir y actualizar, o deseas eliminar?")
                .setNegativeButton("Eliminar"){d,i->
                    idGuardado.eliminarArea()
                    mostrarDatos()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var area = Area(this.requireContext())
                    area.idArea=idDArea
                    area.descrip=binding.txtDescrip.text.toString()
                    area.divis=binding.txtDivis.text.toString()
                    area.cant_emp=binding.txtCantidad.text.toString().toInt()
                    val resul=area.actualizarArea()
                    if(resul){
                        Toast.makeText(this.requireContext(),"SE ACTUALIZO CON EXITO",Toast.LENGTH_LONG).show()
                        mostrarDatos()
                        limpiarCampos()
                    }else{
                        AlertDialog.Builder(this.requireContext())
                            .setTitle("ERROR")
                            .setMessage("NO SE PUDO ACTUALIZAR")
                            .show()
                    }
                }
                .setNeutralButton("Cargar"){ d,i->
                    binding.txtDescrip.setText(idGuardado.descrip)
                    binding.txtDivis.setText(idGuardado.divis)
                    binding.txtCantidad.setText(idGuardado.cant_emp.toString())
                }
                .show()
        }//lista
        return root
    }
    fun limpiarCampos(){
        binding.txtDescrip.setText("")
        binding.txtDivis.setText("")
        binding.txtCantidad.setText("")
    }
    fun mostrarDatos(){
        var listaAreas = Area(this.requireContext()).mostrartblArea()
        var descripArea = ArrayList<String>()
        vectorIDArea.clear()
        (0..listaAreas.size-1).forEach {
            val ar = listaAreas.get(it)
            descripArea.add(ar.descrip)
            vectorIDArea.add(ar.idArea)
        }
        binding.listaHome.adapter = ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_list_item_1, descripArea)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}