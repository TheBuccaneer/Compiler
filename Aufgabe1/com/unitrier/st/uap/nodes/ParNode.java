
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ParNode extends Node
{
	public ParNode()
	{
		super("PAR");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		code.addAll(this.getChildAt(0).code(nl, rho));
		return code;
	}
}
