/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ipti004
 */
@ManagedBean(name = "indexBean")
@SessionScoped
public class IndexBean implements Serializable{
    
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            
        }
    }
    
    public void logout(){
        
    }
}
