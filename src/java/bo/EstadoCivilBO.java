/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.EstadoCivilDAO;
import entidade.EstadoCivil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class EstadoCivilBO implements Serializable {
    
    public static List<EstadoCivil> findAll(){
        try { 
            EstadoCivilDAO estadoCivilDAO = new EstadoCivilDAO();
            return estadoCivilDAO.findEstadoCivilEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EstadoCivil>();
    }
}
