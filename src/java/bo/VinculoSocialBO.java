/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.VinculoSocialDAO;
import entidade.VinculoSocial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulones
 */
public class VinculoSocialBO implements Serializable{
    
    public static List<VinculoSocial> findAll(){
        try { 
            VinculoSocialDAO vinculoSocialDAO = new VinculoSocialDAO();
            return vinculoSocialDAO.findVinculoSocialEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<VinculoSocial>();
    }
}
