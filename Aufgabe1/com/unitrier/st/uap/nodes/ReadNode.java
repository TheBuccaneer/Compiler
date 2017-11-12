
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ReadNode extends Node
{
	public ReadNode()
	{
		super("READ");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		IDNode id = (IDNode) this.getChildAt(0);
		AddressPair idMemory = rho.get(id.getAttributeToString());
		int k = (Integer) idMemory.loc;
		int nl1 = idMemory.nl;
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		code.add(new Instruction(Instruction.LOAD, k, nl - nl1));
		return code;
	}

}
