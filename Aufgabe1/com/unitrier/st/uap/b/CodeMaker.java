package com.unitrier.st.uap.b;

import java.util.ArrayList;
import java.util.HashMap;

import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;
import com.unitrier.st.uap.nodes.Node;

public class CodeMaker
{
	/**
	 * generates tram code
	 * 
	 * @param ast
	 * @return
	 */
	public ArrayList<Instruction> generate(Node ast)
	{
		ArrayList<Instruction> code = new ArrayList<Instruction>();
		code.addAll(ast.code(0, new HashMap<String, AddressPair>()));
		code = this.replaceTramLabels(code);

		return code;
	}

	/**
	 * replaces tramlables
	 * 
	 * @param code
	 * @return
	 */
	public ArrayList<Instruction> replaceTramLabels(ArrayList<Instruction> code)
	{
		HashMap<String, Integer> addresses = new HashMap<String, Integer>();
		int instNum = 0;
		// for (Instruction i : code)
		// System.out.println(i);
		for (instNum = 0; instNum < code.size();)
		{
			Instruction tmp = code.get(instNum);
			if (((Integer) tmp.opcode).equals(Instruction.TRAMLABEL))
			{
				addresses.put(tmp.key, instNum);
				code.remove(instNum);
			} else
				instNum++;
		}
		// System.out.println();
		// for (Instruction i : code)
		// System.out.println(i);

		for (int i = 0; i < code.size(); i++)
		{
			Instruction tmp = code.get(i);

			if (((Integer) tmp.opcode).equals(Instruction.TLCALLER))
			{
				int pos = addresses.get(tmp.key);
				Instruction einfuegeInstruction = tmp.arg4;
				switch (tmp.exchangeSpot)
				{
				case 1:
					einfuegeInstruction.arg1 = pos;
					break;
				case 2:
					einfuegeInstruction.arg2 = pos;
					break;
				case 3:
					einfuegeInstruction.arg3 = pos;
					break;
				}
				code.add(i, einfuegeInstruction);
				code.remove(i + 1);
			}

		}

		// while (instNum < code.size())
		// {
		// Instruction tmp = code.get(instNum);
		// if (((Integer) tmp.opcode).equals(Instruction.TRAMLABEL))
		// {
		// rho.put(tmp.key, instNum);
		// code.remove(instNum);
		// } else
		// {
		// instNum++;
		// }
		// }

		code.add(new Instruction(Instruction.HALT));
		return code;

	}
}
