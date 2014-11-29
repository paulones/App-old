/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.CitacaoHistoricoDAO;
import entidade.CitacaoHistorico;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class CitacaoHistoricoBO implements Serializable{
    
    private CitacaoHistoricoDAO citacaoHistoricoDAO;

    public CitacaoHistoricoBO() {
        citacaoHistoricoDAO = new CitacaoHistoricoDAO();
    }
    
    public void create(CitacaoHistorico citacaoHistorico){
        try {
            citacaoHistoricoDAO.create(citacaoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(CitacaoHistorico citacaoHistorico){
        try {
            citacaoHistoricoDAO.edit(citacaoHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(CitacaoHistorico citacaoHistorico){
        try {
            citacaoHistoricoDAO.destroy(citacaoHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
