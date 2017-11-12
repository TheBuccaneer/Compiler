package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class IDNode extends Node
{
	public IDNode(String value)
	{
		super("ID", value);
	}

	public boolean equals(Object type)
	{
		if (!(type instanceof IDNode))
			return false;
		return this.getAttributeToString().equals(((IDNode) type).getAttributeToString());
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		System.err.println("Should not be invokes. ID CLASS");
		return null;
	}

}
