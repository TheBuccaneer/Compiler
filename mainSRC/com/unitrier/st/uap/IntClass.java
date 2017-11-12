package com.unitrier.st.uap;

public class IntClass
{
	public int val;

	public IntClass(int val)
	{
		this.val = val;
	}

	@Override
	public String toString()
	{
		return ("( " + val + " )");
	}
}
