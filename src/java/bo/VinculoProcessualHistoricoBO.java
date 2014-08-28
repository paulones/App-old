/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.VinculoProcessualHistoricoDAO;
import entidade.VinculoProcessualHistorico;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class VinculoProcessualHistoricoBO implements Serializable{
    
    private VinculoProcessualHistoricoDAO vinculoProcessualHistoricoDAO;
    
    public VinculoProcessualHistoricoBO(){
        vinculoProcessualHistoricoDAO = new VinculoProcessualHistoricoDAO();
    }
    
    public void create(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            vinculoProcessualHistoricoDAO.create(vinculoProcessualHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            vinculoProcessualHistoricoDAO.edit(vinculoProcessualHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            vinculoProcessualHistoricoDAO.destroy(vinculoProcessualHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
