
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class CallNode extends Node
{
	public CallNode()
	{
		super("CALL");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{

		ArrayList<Instruction> code = new ArrayList<Instruction>();

		ArgsNode args = (ArgsNode) this.getChildAt(1);
		code.addAll(args.code(nl, rho));

		String id = this.getChildAttributeAt(0);
		int functionParams = this.getChildren().getLast().getChildren().size();
		AddressPair idMemory = rho.get(id);
		int nlDiff = nl - idMemory.nl;
		int insertPos = 2;
		Instruction invokeInstruction = new Instruction(Instruction.INVOKE, functionParams, -1, nlDiff);
		code.add(new Instruction(Instruction.TLCALLER, idMemory.loc.toString(), invokeInstruction, insertPos));
		return code;
	}

}
