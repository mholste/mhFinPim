package de.mho.finpim.util;

import java.util.Date;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

/**
 * @author Michael Holste
 * 
 * Comparator-Klasse,um in einer Tabellenach Datum sortieren zu können.
 *
 */

public class DateComparator extends ViewerComparator 
{
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;
	
	public DateComparator()
	{
		this.propertyIndex = 0;
		direction = DESCENDING;
	}
	
	public int getDirection()
	{
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}
	
	public void setColumn (int column)
	{
		if (column == this.propertyIndex)
		{
			direction = 1 - direction;
		}
		else
		{
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}
	
	@Override
	public int compare (Viewer viewer, Object o1, Object o2)
	{
		Date d1 = (Date) o1;
		Date d2 = (Date) o2;
		
		return d1.compareTo(d2);		
	}

}
