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
    
    public static void create(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            VinculoProcessualHistoricoDAO vinculoProcessualHistoricoDAO = new VinculoProcessualHistoricoDAO();
            vinculoProcessualHistoricoDAO.create(vinculoProcessualHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            VinculoProcessualHistoricoDAO vinculoProcessualHistoricoDAO = new VinculoProcessualHistoricoDAO();
            vinculoProcessualHistoricoDAO.edit(vinculoProcessualHistorico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(VinculoProcessualHistorico vinculoProcessualHistorico){
        try {
            VinculoProcessualHistoricoDAO vinculoProcessualHistoricoDAO = new VinculoProcessualHistoricoDAO();
            vinculoProcessualHistoricoDAO.destroy(vinculoProcessualHistorico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
