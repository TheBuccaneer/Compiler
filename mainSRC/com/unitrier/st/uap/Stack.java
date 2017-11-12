package com.unitrier.st.uap;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Stack<E> extends ArrayList<E>
{

	public void push(int pos, E obj)
	{
		if (pos >= this.size())
		{
			this.add(obj);

		} else if (pos < this.size())
		{
			this.set(pos, obj);
		}
	}
}
