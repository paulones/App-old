/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.VinculoProcessualDAO;
import entidade.VinculoProcessual;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class VinculoProcessualBO implements Serializable{
    
    public static void create(VinculoProcessual vinculoProcessual){
        try {
            VinculoProcessualDAO vinculoProcessualDAO = new VinculoProcessualDAO();
            vinculoProcessualDAO.create(vinculoProcessual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void edit(VinculoProcessual vinculoProcessual){
        try {
            VinculoProcessualDAO vinculoProcessualDAO = new VinculoProcessualDAO();
            vinculoProcessualDAO.edit(vinculoProcessual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(VinculoProcessual vinculoProcessual){
        try {
            VinculoProcessualDAO vinculoProcessualDAO = new VinculoProcessualDAO();
            vinculoProcessualDAO.destroy(vinculoProcessual.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroyByPJUD(Integer idPjud){
        try {
            VinculoProcessualDAO vinculoProcessualDAO = new VinculoProcessualDAO();
            vinculoProcessualDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
