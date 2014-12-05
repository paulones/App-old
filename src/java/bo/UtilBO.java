/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import dao.UtilDAO;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author paulones
 */
public class UtilBO implements Serializable{
    
    public static Timestamp findServerTime() {
        try { 
            UtilDAO utilDAO = new UtilDAO();
            return utilDAO.findServerTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
