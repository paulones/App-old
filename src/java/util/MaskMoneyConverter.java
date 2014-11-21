/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author paulones
 */
@FacesConverter(value = "maskMoneyConverter")
public class MaskMoneyConverter implements Converter {

    public Object getAsObject(FacesContext ctx, UIComponent component,
            String value) {
        if (value != null) {
            value = value.replace(".","").replace(",", ".").replace(" ", "").replace("R$", "");
            return value;
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component,
            Object obj) {
        return obj.toString();
    }
}