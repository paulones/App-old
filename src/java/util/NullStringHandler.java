/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;

/**
 *
 * @author paulones
 */
public class NullStringHandler  extends ELResolver
{

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base)
	{
		
		return String.class;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
			Object base)
	{
		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property)
	{
		
		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property)
	{
		
		return null;
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
	{
		
		return true;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property,
			Object value)
	{
		
		
	}

	@Override
	public Object convertToType(ELContext context, Object obj,
			Class<?> targetType)
	{
		if (obj==null && String.class.equals(targetType))
		{
			context.setPropertyResolved(true);;
			return null;
		}
		return null;
			
		
	}
	

}

