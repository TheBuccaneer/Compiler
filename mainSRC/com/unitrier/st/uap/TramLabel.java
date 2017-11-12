package com.unitrier.st.uap;

public class TramLabel
{
	private int address;

	public TramLabel(int a)
	{
		this.address = a;
	}

	@Override
	public boolean equals(Object tl)
	{
		if (tl instanceof Integer)
		{
			return false;
		}

		// ansonsten werden zwei tramLabel verglichen
		else if (this.address == ((TramLabel) tl).address)
		{
			return true;
		} else
		{
			return false;
		}
	}
}
