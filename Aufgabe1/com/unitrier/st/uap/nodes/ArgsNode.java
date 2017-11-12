
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ArgsNode extends Node
{
	public ArgsNode()
	{
		super("ARGS");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();

		for (int i = 0; i < this.getChildren().size(); i++)
		{
			code.addAll(this.getChildren().get(i).code(nl, rho));
		}
		return code;
	}
}
