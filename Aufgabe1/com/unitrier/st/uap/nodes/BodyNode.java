
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class BodyNode extends Node
{
	public BodyNode()
	{
		super("BODY");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> tramCode = new ArrayList<Instruction>();
		tramCode.addAll(this.getChildAt(0).code(nl, rho));
		return tramCode;
	}
}
