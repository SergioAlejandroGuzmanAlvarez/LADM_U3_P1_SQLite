package com.example.ladm_u3_p1_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import com.example.ladm_u3_p1_sqlite.ui.home.HomeFragment

class Area (este:Context) {
    private var este = este
    var descrip=""
    var divis=""
    var cant_emp=0
    var idArea=0
    private var err=""
    fun eliminarArea(): Boolean{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        err=""
        try{
            val tblArea = baseDatos.writableDatabase
            var resultado = tblArea.delete("AREA","IDAREA='${idArea}'",null)
            if(resultado==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun actualizarArea():Boolean{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        err=""
        try{
            val tblArea = baseDatos.writableDatabase
            var data = ContentValues()
            data.put("DESCRIPCION", descrip)
            data.put("DIVISION", divis)
            data.put("CANTIDAD_EMPLEADOS",cant_emp)
            val resultado= tblArea.update("AREA",data,"IDAREA=${idArea}", null)
            if(resultado==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun insertar(): Boolean{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        err=""
        try{
            val tblArea = baseDatos.writableDatabase
            var data = ContentValues()
            data.put("DESCRIPCION", descrip)
            data.put("DIVISION", divis)
            data.put("CANTIDAD_EMPLEADOS",cant_emp)
            var resultado = tblArea.insert("AREA",null,data)
            if(resultado==-1L){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
    fun mostrartblArea() : ArrayList<Area>{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var arreglo = ArrayList<Area>()
        err = ""
        try{
            var tblArea = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AREA"

            var CURSOR = tblArea.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do {
                    var area = Area(este)
                    area.idArea = CURSOR.getInt(0)
                    area.descrip = CURSOR.getString(1)
                    area.divis = CURSOR.getString(2)
                    area.cant_emp = CURSOR.getInt(3)
                    arreglo.add(area)
                }while(CURSOR.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }
    fun mostrarSoloID(idArea:Int): Area{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        val idRegreso = Area(este)
        err=""
        try{
            var tblArea=baseDatos.readableDatabase
            var SQL_SELECT="SELECT * FROM AREA WHERE IDAREA=${idArea}"
            var CURSOR=tblArea.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                idRegreso.idArea=CURSOR.getInt(0)
                idRegreso.descrip=CURSOR.getString(1)
                idRegreso.divis=CURSOR.getString(2)
                idRegreso.cant_emp=CURSOR.getInt(3)
            }
        }catch(err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return idRegreso
    }
}