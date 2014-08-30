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
    
    private VinculoProcessualDAO vinculoProcessualDAO;
    
    public VinculoProcessualBO(){
        vinculoProcessualDAO = new VinculoProcessualDAO();
    }
    
    public void create(VinculoProcessual vinculoProcessual){
        try {
            vinculoProcessualDAO.create(vinculoProcessual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(VinculoProcessual vinculoProcessual){
        try {
            vinculoProcessualDAO.edit(vinculoProcessual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(VinculoProcessual vinculoProcessual){
        try {
            vinculoProcessualDAO.destroy(vinculoProcessual.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroyByPJUD(Integer idPjud){
        try {
            vinculoProcessualDAO.destroyByPJUD(idPjud);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
