package com.example.ladm_u3_p1_sqlite.ui.notifications

import android.R
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
import com.example.ladm_u3_p1_sqlite.Subdepartamento
import com.example.ladm_u3_p1_sqlite.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    var vectorIDSub = ArrayList<Int>()
    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatos()
        binding.btninsertarSub.setOnClickListener {
            var sub = Subdepartamento(this.requireContext())
            sub.idEdificio = binding.txtidEdficio.text.toString()
            sub.pisoS = binding.txtPiso.text.toString()
            sub.idAreaS = binding.txtidAreaS.text.toString().toInt()
            val res = sub.insertar()
            if(res){
                AlertDialog.Builder(this.requireContext())
                    .setTitle("[AREA INSERTADA]")
                    .setMessage("Se inserto un nuevo subdepartamento a la BD.")
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
        binding.listaSub.setOnItemClickListener { adapterView, view, i, l ->
            val idSub = vectorIDSub.get(i)
            val idAcumulado = Subdepartamento(this.requireContext()).mostrartblSubID(idSub)
            AlertDialog.Builder(this.requireContext())
                .setTitle("¿Que deseas hacer?")
                .setMessage("Seleccionaste el ${idAcumulado.idSubdepto}"+" del sub.departamento con piso: ${idAcumulado.pisoS}\n ¿Qué deseas hacer? ")
                .setNegativeButton("Eliminar"){d,i->
                    idAcumulado.eliminarSub()
                    mostrarDatos()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var sub = Subdepartamento(this.requireContext())
                    sub.idSubdepto = idSub
                    sub.idEdificio=binding.txtidEdficio.text.toString()
                    sub.pisoS=binding.txtPiso.text.toString()
                    sub.idAreaS=binding.txtidAreaS.text.toString().toInt()
                    val resul=sub.actualizarSub()
                    if(resul){
                        Toast.makeText(this.requireContext(),"SE ACTUALIZO CON EXITO", Toast.LENGTH_LONG).show()
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
                    binding.txtidEdficio.setText(idAcumulado.idEdificio)
                    binding.txtPiso.setText(idAcumulado.pisoS)
                    binding.txtidAreaS.setText(idAcumulado.idAreaS.toString())
                }
                .show()
        }
        return root
    }
    fun limpiarCampos(){
        binding.txtPiso.setText("")
        binding.txtidAreaS.setText("")
        binding.txtidEdficio.setText("")
    }
    fun mostrarDatos(){
        var listaSubdepartamentos = Subdepartamento(this.requireContext()).mostrartblSub()
        var arrayPiso = ArrayList<String>()
        vectorIDSub.clear()
        (0..listaSubdepartamentos.size-1).forEach {
            val ar = listaSubdepartamentos.get(it)
            arrayPiso.add("ID EDIFICIO:"+ar.idEdificio+"PISO:"+ar.pisoS+" ID DEL AREA:"+ar.idAreaS)
            vectorIDSub.add(ar.idSubdepto)
        }
        binding.listaSub.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1, arrayPiso)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}