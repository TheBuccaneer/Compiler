
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class ExprNode extends Node
{
	public ExprNode()
	{
		super("EXPR");
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		System.err.println("Schould not be invoked. Expr Class");
		return null;
	}
}
