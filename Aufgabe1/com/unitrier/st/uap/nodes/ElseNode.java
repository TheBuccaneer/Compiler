
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ElseNode extends Node
{
	public ElseNode()
	{
		super("ELSE");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		System.err.println("Schould not be invoked. Else Class");
		return null;
	}
}
