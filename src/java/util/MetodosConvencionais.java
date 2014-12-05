/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author paulones
 */
public class MetodosConvencionais implements Serializable{
    
    public static boolean checarListasIguais(String tipo, List<?> list, List<?> oldList) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        boolean igual = true;
        if (oldList.size() != list.size()) {
            igual = false;
        } else {
            for (Object object : list) {
                for (Object oldObject : oldList) {
                    Class<?> c = Class.forName("entidade." + tipo);
                    Method method = c.getDeclaredMethod("equalsValues", Object.class);
                    boolean equals = (boolean) method.invoke(object, oldObject);
                    if (equals) {
                        igual = true;
                        break;
                    } else {
                        igual = false;
                    }
                }
                if (!igual) {
                    break;
                }
            }
        }
        return igual;
    }
}
