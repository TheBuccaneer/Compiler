
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;
import com.unitrier.st.uap.TramLabel;

public class LetNode extends Node
{
	public LetNode()
	{
		super("LET");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> tramCode = new ArrayList<Instruction>();
		HashMap<String, AddressPair> rho2 = ((DefNode) this.getChildAt(0)).elab_def(rho, nl);
		addLabel(nl, rho);
		int label = labelCount;
		int insIndex = 1;
		Instruction insINstuktion = new Instruction(Instruction.GOTO, -1);

		tramCode.add(new Instruction(Instruction.TLCALLER, Integer.toString(label), insINstuktion, insIndex));
		tramCode.addAll(this.getChildAt(0).code(nl, rho2));
		tramCode.add(new Instruction(Instruction.TRAMLABEL, Integer.toString(label)));
		tramCode.addAll(this.getChildAt(1).code(nl, rho2));
		return tramCode;
	}

	public HashMap<String, AddressPair> elab_def(HashMap<String, AddressPair> rho, int nl)
	{
		IDNode d1 = (IDNode) this.getChildren().get(0);
		rho.put(d1.getAttributeToString(), new AddressPair(new TramLabel(-1), nl));
		return rho;
	}
}
