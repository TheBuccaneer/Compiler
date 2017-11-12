
package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;

public class OpNode extends Node
{
	public static final String ADD = "+";
	public static final String SUB = "-";
	public static final String MULT = "*";
	public static final String DIV = "/";
	public static final String EQ = "==";
	public static final String NEQ = "!=";
	public static final String LT = "&lt;";
	public static final String GT = "&gt;";
	public static final String AND = "&amp;&amp;";
	public static final String OR = "||";

	public OpNode(String value)
	{
		super("OP", value);
	}

	@Override
	public ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();

		code.addAll(this.getChildAt(0).code(nl, rho));
		code.addAll(this.getChildAt(1).code(nl, rho));

		Instruction inst;
		switch (this.getAttribute().toString())
		{
		case "==":
			inst = new Instruction(Instruction.EQ);
			break;
		case "!=":
			inst = new Instruction(Instruction.NEQ);
			break;
		case "&lt;":
			inst = new Instruction(Instruction.LT);
			break;
		case "&gt;":
			inst = new Instruction(Instruction.GT);
			break;
		case "+":
			inst = new Instruction(Instruction.ADD);
			break;
		case "-":
			inst = new Instruction(Instruction.SUB);
			break;
		case "*":
			inst = new Instruction(Instruction.MUL);
			break;
		case "/":
			inst = new Instruction(Instruction.DIV);
			break;
		case "&amp;&amp;":
			inst = new Instruction(Instruction.AND);
			break;
		case "||":
			inst = new Instruction(Instruction.OR);
			break;
		default:
			inst = new Instruction(-100);
			System.out.println("Error in OpNode Class");
		}
		code.add(inst);
		return code;
	}
}
