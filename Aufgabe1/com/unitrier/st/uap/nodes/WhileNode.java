package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class WhileNode extends Node
{
	public WhileNode()
	{
		super("WHILE");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();

		ArrayList<Instruction> condition = this.getChildren().get(0).getChildren().get(0).code(nl, rho);
		ArrayList<Instruction> expresion = this.getChildren().get(1).getChildren().get(0).code(nl, rho);

		addLabel(nl, rho);

		int l1 = labelCount;
		int l2 = l1 + 1;

		int insIndex = 1;
		code.add(new Instruction(Instruction.TRAMLABEL, Integer.toString(l1)));

		code.addAll(expresion);
		code.addAll(condition);

		code.add(new Instruction(Instruction.TLCALLER, Integer.toString(l2), new Instruction(Instruction.IFZERO, -1),
				insIndex));

		code.add(new Instruction(Instruction.TLCALLER, Integer.toString(l1), new Instruction(Instruction.GOTO, -1),
				insIndex));

		addLabel(nl, rho);
		l2 = labelCount;
		code.add(new Instruction(Instruction.TRAMLABEL, Integer.toString(l2)));

		return code;
	}
}
