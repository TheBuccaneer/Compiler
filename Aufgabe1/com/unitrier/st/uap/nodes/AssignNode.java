
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class AssignNode extends Node
{
	public AssignNode()
	{
		super("ASSIGN");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		Node node = this.getChildAt(1);
		IDNode ID = (IDNode) this.getChildAt(0);
		AddressPair variable = rho.get(ID.getAttribute().toString());

		int nl1 = variable.nl;
		int k = (Integer) variable.loc;

		ArrayList<Instruction> code = node.code(nl, rho);
		code.add(new Instruction((Instruction.STORE), k, nl - nl1));
		code.add(new Instruction((Instruction.LOAD), k, nl - nl1));
		return code;
	}
}
