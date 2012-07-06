/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.model.controller;

import com.pmarlen.model.beans.Almacen;

/**
 *
 * @author aestrada
 */
interface AlmacenDao {

    void save(Almacen Almacen);

    Almacen update(Almacen Almacen);

    void delete(Almacen Almacen);
}
