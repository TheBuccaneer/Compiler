
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class DefNode extends Node
{
	public DefNode()
	{
		super("DEF");
	}

	public HashMap<String, AddressPair> elab_def(HashMap<String, AddressPair> rho, int nl)
	{
		@SuppressWarnings("unchecked")
		HashMap<String, AddressPair> rho2 = (HashMap<String, AddressPair>) rho.clone();
		for (int i = 0; i < this.getChildren().size(); i++)
		{
			rho2 = ((FuncNode) this.getChildAt(i)).elab_def(rho2, nl);
		}
		return rho2;
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		ListIterator<Node> iterator = this.getChildren().listIterator();
		while (iterator.hasNext())
		{
			code.addAll(iterator.next().code(nl, rho));
		}
		return code;
	}
}
