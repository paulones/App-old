/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.CitacaoDAO;
import entidade.Citacao;
import java.io.Serializable;

/**
 *
 * @author paulones
 */
public class CitacaoBO implements Serializable{
    
    private CitacaoDAO citacaoDAO;

    public CitacaoBO() {
        citacaoDAO = new CitacaoDAO();
    }
    
    public void create(Citacao citacao){
        try {
            citacaoDAO.create(citacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit(Citacao citacao){
        try {
            citacaoDAO.edit(citacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void destroy(Citacao citacao){
        try {
            citacaoDAO.destroy(citacao.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
