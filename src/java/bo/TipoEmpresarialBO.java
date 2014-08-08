/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.TipoEmpresarialDAO;
import entidade.TipoEmpresarial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class TipoEmpresarialBO  implements Serializable{
    
    TipoEmpresarialDAO tipoEmpresarialDAO;
    
    public TipoEmpresarialBO (){
        tipoEmpresarialDAO = new TipoEmpresarialDAO();
    }
    
    public List<TipoEmpresarial> findAll(){
        try { 
            return tipoEmpresarialDAO.findTipoEmpresarialEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TipoEmpresarial>();
    }
}
