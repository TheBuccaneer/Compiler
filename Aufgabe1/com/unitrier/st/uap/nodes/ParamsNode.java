
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ParamsNode extends Node
{
	public ParamsNode()
	{
		super("PARAMS");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		System.err.println("Should not be invoked. ParamsNode Class");
		return null;
	}

}
