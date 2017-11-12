
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class SemiNode extends Node
{
	public SemiNode()
	{
		super("SEMI");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		for (int i = 0; i < this.getChildren().size() - 1; i++)
		{
			code.addAll(this.getChildAt(i).code(nl, rho));
		}
		code.addAll(this.getChildren().get(this.getChildren().size() - 1).code(nl, rho));
		return code;
	}
}
