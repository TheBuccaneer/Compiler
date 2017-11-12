
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class IfNode extends Node
{
	public IfNode()
	{
		super("IF");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();

		ArrayList<Instruction> e1 = this.getChildAt(0).getChildAt(0).code(nl, rho);
		ArrayList<Instruction> e2 = this.getChildAt(1).getChildAt(0).code(nl, rho);
		ArrayList<Instruction> e3 = this.getChildAt(2).getChildAt(0).code(nl, rho);

		code.addAll(e1);

		addLabel(nl, rho);
		int label1 = labelCount;
		Instruction insertInstruction = new Instruction(Instruction.IFZERO, -1);
		int insertIndex = 1;
		code.add(new Instruction(Instruction.TLCALLER, Integer.toString(label1), insertInstruction, insertIndex));
		code.addAll(e2);
		addLabel(nl, rho);
		int label2 = labelCount;
		insertInstruction = new Instruction(Instruction.GOTO, -1);
		code.add(new Instruction(Instruction.TLCALLER, Integer.toString(label2), insertInstruction, insertIndex));
		code.add(new Instruction(Instruction.TRAMLABEL, Integer.toString(label1)));
		code.addAll(e3);
		code.add(new Instruction(Instruction.TRAMLABEL, Integer.toString(label2)));
		code.add(new Instruction(Instruction.NOP));

		return code;
	}
}
