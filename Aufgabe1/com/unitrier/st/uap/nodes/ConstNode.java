
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ConstNode extends Node
{
	public ConstNode(Integer value)
	{
		super("CONST", value);
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		Integer constValue = Integer.decode(this.getAttributeToString());

		ArrayList<Instruction> code = new ArrayList<Instruction>();
		code.add(new Instruction(Instruction.CONST, constValue));
		return code;
	}

}
