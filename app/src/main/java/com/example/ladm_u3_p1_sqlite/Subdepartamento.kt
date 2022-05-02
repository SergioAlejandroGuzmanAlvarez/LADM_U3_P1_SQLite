package com.example.ladm_u3_p1_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Subdepartamento(este: Context) {
    var idSubdepto=0
    var idEdificio=""
    var pisoS=""
    var idAreaS=0
    private var este=este
    var err=""
    fun eliminarSub() : Boolean{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        err=""
        try{
            val tblSub = baseDatos.writableDatabase
            var resultado = tblSub.delete("SUBDEPARTAMENTO","IDSUBDEPTO='${idSubdepto}'",null)
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
    fun actualizarSub() : Boolean{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        err=""
        try{
            val tblSub = baseDatos.writableDatabase
            var data = ContentValues()
            data.put("IDEDIFICIO", idEdificio)
            data.put("PISO", pisoS)
            data.put("IDAREA",idAreaS)
            val res=tblSub.update("SUBDEPARTAMENTO",data,"IDSUBDEPTO='${idSubdepto}'",null)
            if(res==0){
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
            data.put("IDEDIFICIO", idEdificio)
            data.put("PISO", pisoS)
            data.put("IDAREA",idAreaS)
            var resultado = tblArea.insert("SUBDEPARTAMENTO",null,data)
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
    fun mostrartblSub() : ArrayList<Subdepartamento>{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var arreglo = ArrayList<Subdepartamento>()
        err = ""
        try{
            var tblSub = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM SUBDEPARTAMENTO"

            var CURSOR = tblSub.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do {
                    var subCursor = Subdepartamento(este)
                    subCursor.idSubdepto = CURSOR.getInt(0)
                    subCursor.idEdificio = CURSOR.getString(1)
                    subCursor.pisoS = CURSOR.getString(2)
                    subCursor.idAreaS = CURSOR.getInt(3)
                    arreglo.add(subCursor)
                }while(CURSOR.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }
    fun mostraridEdficio(idEdif:Int): ArrayList<Subdepartamento>
    {
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var vectorSub = ArrayList<Subdepartamento>()
        err=""
        try{
            var tblSub=baseDatos.readableDatabase
            var SQL_SELECT= "SELECT SUB.ID_SUBDEPTO, SUB.ID_EDIFICIO, SUB.PISO, AR.DESCRIPCION, AR.DIVISION, AR.ID_AREA " +
                    "FROM SUBDEPARTAMENTO SUB " +
                    "INNER JOIN AREA AR " +
                    "ON A.ID_AREA = S.ID_AREA " +
                    "WHERE SUB.ID_EDIFICIO LIKE '${idEdif}%'"
            var CURSOR=tblSub.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do{
                    val sub2=Subdepartamento(este)
                    sub2.idSubdepto=CURSOR.getInt(0)
                    sub2.idEdificio=CURSOR.getString(1)
                    sub2.pisoS=CURSOR.getString(2)
                    sub2.idAreaS=CURSOR.getInt(3)
                    vectorSub.add(sub2)
                }while(CURSOR.moveToNext())
            }//IF
        }catch(err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return vectorSub
    }
    fun mostrartblSubID(idSubD: Int) : Subdepartamento{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var puntero = Subdepartamento(este)
        err = ""
        try{
            var tblSub = baseDatos.readableDatabase
            var SQL_SELECT="SELECT * FROM SUBDEPARTAMENTO WHERE IDSUBDEPTO=${idSubD}"
            var CURSOR = tblSub.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do {
                    puntero.idSubdepto = CURSOR.getInt(0)
                    puntero.idEdificio = CURSOR.getString(1)
                    puntero.pisoS = CURSOR.getString(2)
                    puntero.idAreaS = CURSOR.getInt(3)
                }while(CURSOR.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return puntero
    }
    fun mostrarDescripArea(descrip:String):ArrayList<Subdepartamento>{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var vectorDescrip = ArrayList<Subdepartamento>()
        err=""
        try{
            var tblSub = baseDatos.readableDatabase
            var SQL_SELECT= "SELECT SUB.ID_SUBDEPTO, SUB.ID_EDIFICIO, SUB.PISO, AR.DESCRIPCION, AR.DIVISION, AR.ID_AREA " +
                    "FROM SUBDEPARTAMENTO SUB " +
                    "INNER JOIN AREA AR " +
                    "ON AR.ID_AREA = SUB.ID_AREA " +
                    "WHERE AR.DESCRIPCION LIKE '${descrip}%'"
            var CURSOR = tblSub.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do {
                    var sub = Subdepartamento(este)
                    sub.idSubdepto = CURSOR.getInt(0)
                    sub.idEdificio = CURSOR.getString(1)
                    sub.pisoS = CURSOR.getString(2)
                    sub.idAreaS = CURSOR.getInt(3)
                    vectorDescrip.add(sub)
                }while(CURSOR.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return vectorDescrip
    }
    fun mostrarDiviArea(divs:String):ArrayList<Subdepartamento>{
        var baseDatos = BaseDatos(este, "recursos", null,1)
        var vectorDescrip = ArrayList<Subdepartamento>()
        err=""
        try{
            var tblSub = baseDatos.readableDatabase
            var SQL_SELECT= "SELECT SUB.ID_SUBDEPTO, SUB.ID_EDIFICIO, SUB.PISO, AR.DESCRIPCION, AR.DIVISION, AR.ID_AREA " +
                    "FROM SUBDEPARTAMENTO SUB " +
                    "INNER JOIN AREA AR " +
                    "ON AR.ID_AREA = SUB.ID_AREA " +
                    "WHERE AR.DIVISION LIKE '${divs}%'"
            var CURSOR = tblSub.rawQuery(SQL_SELECT,null)
            if(CURSOR.moveToFirst()){
                do {
                    var sub = Subdepartamento(este)
                    sub.idSubdepto = CURSOR.getInt(0)
                    sub.idEdificio = CURSOR.getString(1)
                    sub.pisoS = CURSOR.getString(2)
                    sub.idAreaS = CURSOR.getInt(3)
                    vectorDescrip.add(sub)
                }while(CURSOR.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return vectorDescrip
    }
}