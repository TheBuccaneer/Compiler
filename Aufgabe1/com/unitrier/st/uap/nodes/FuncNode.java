
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;
import com.unitrier.st.uap.TramLabel;

public class FuncNode extends Node
{
	public FuncNode()
	{
		super("FUNC");
	}

	public HashMap<String, AddressPair> elab_def(HashMap<String, AddressPair> rho, int nl)
	{
		IDNode d1 = (IDNode) this.getChildren().get(0);
		rho.put(d1.getAttribute().toString(), new AddressPair(new TramLabel(-1), nl));
		return rho;
	}

	@Override
	public ArrayList<Instruction> code(int nl1, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		@SuppressWarnings("unchecked")
		HashMap<String, AddressPair> rho2 = (HashMap<String, AddressPair>) rho.clone();

		IDNode signature = (IDNode) this.getChildAt(0);

		String funcKey = signature.getAttributeToString();
		int nl = rho2.get(funcKey).nl;
		String label = rho2.get(funcKey).loc.toString();

		code.add(new Instruction(Instruction.TRAMLABEL, label));

		ParamsNode par = (ParamsNode) this.getChildAt(1);

		for (int i = 0; i < par.getChildren().size(); i++)
		{
			String key = par.getChildAttributeAt(i);
			rho2.put(key, new AddressPair(i, nl + 1));
		}
		code.addAll(this.getChildAt(2).getChildAt(0).code(nl + 1, rho2));
		code.add(new Instruction(Instruction.RETURN));

		return code;
	}
}
