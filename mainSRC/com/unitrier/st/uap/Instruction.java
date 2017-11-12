
package com.unitrier.st.uap;

public class Instruction
{

	public TramLabel label;

	public int opcode;
	public Integer arg1;
	public Integer arg2;
	public Integer arg3;
	public Instruction arg4;
	public String key;
	public Integer exchangeSpot;

	public final static int CONST = 1;
	public final static int LOAD = 2;
	public final static int STORE = 3;
	public final static int ADD = 4;
	public final static int SUB = 5;
	public final static int MUL = 6;
	public final static int DIV = 7;
	public final static int LT = 8;
	public final static int GT = 9;
	public final static int EQ = 10;
	public final static int NEQ = 11;
	public final static int IFZERO = 12;
	public final static int GOTO = 13;
	public final static int HALT = 14;
	public final static int NOP = 15;
	public final static int INVOKE = 16;
	public final static int RETURN = 17;
	public final static int TRAMLABEL = 18;
	public final static int TLCALLER = 19;
	public final static int AND = 20;
	public final static int OR = 21;

	// Beginn Konstruktoren
	// Funk
	public Instruction(int opcode, String key)
	{
		this.opcode = opcode;
		this.key = key;

	}

	// Variable
	public Instruction(int opcode, String key, Instruction ins)
	{
		this.opcode = opcode;
		this.key = key;
		this.arg4 = ins;

	}

	// Invoke
	public Instruction(int opcode, String key, Instruction invoke, int einsetzStelle)
	{
		this(opcode);
		this.key = key;
		this.arg4 = invoke;
		this.exchangeSpot = einsetzStelle;
	}

	// Invoke
	public Instruction(int opcode, Integer arg1, Instruction invoke, int einsetzStelle)
	{
		this(opcode, arg1);
		this.arg4 = invoke;
		this.exchangeSpot = einsetzStelle;
	}

	// Wert einsetzen
	public Instruction(int opcode, Integer arg1, Integer arg2, Integer arg3, Integer ersatzStelle)
	{
		this(opcode, arg1, arg2, arg3);

		this.exchangeSpot = ersatzStelle;
	}

	public Instruction(int opcode, Integer arg1, Integer arg2, Integer arg3)
	{
		this(opcode, arg1, arg2);
		this.arg3 = arg3;
	}

	public Instruction(int opcode, Integer arg1, Integer arg2)
	{
		this(opcode, arg1);
		this.arg2 = arg2;
	}

	public Instruction(int opcode, Integer arg1)
	{
		this(opcode);
		this.arg1 = arg1;
	}

	public Instruction(int opcode)
	{
		this.opcode = opcode;
	}

	public int getArg1()
	{
		return arg1;
	}

	public void setArg1(Integer arg1)
	{
		this.arg1 = arg1;
	}

	public int getArg2()
	{
		return arg2;
	}

	public void setArg2(Integer arg2)
	{
		this.arg2 = arg2;
	}

	public int getArg3()
	{
		return arg3;
	}

	public void setArg3(Integer arg3)
	{
		this.arg3 = arg3;
	}

	public int getOpcode()
	{
		return opcode;
	}

	public void setOpcode(int opcode)
	{
		this.opcode = opcode;
	}

	@Override
	public String toString()
	{
		String retStr = "";

		switch (opcode)
		{
		case Instruction.CONST:
			retStr += "CONST";
			break;
		case Instruction.LOAD:
			retStr += "LOAD";
			break;
		case Instruction.STORE:
			retStr += "STORE";
			break;
		case Instruction.ADD:
			retStr += "ADD";
			break;
		case Instruction.SUB:
			retStr += "SUB";
			break;
		case Instruction.MUL:
			retStr += "MUL";
			break;
		case Instruction.DIV:
			retStr += "DIV";
			break;
		case Instruction.LT:
			retStr += "LT";
			break;
		case Instruction.GT:
			retStr += "GT";
			break;
		case Instruction.EQ:
			retStr += "EQ";
			break;
		case Instruction.NEQ:
			retStr += "NEQ";
			break;
		case Instruction.IFZERO:
			retStr += "IFZERO";
			break;
		case Instruction.GOTO:
			retStr += "GOTO";
			break;
		case Instruction.HALT:
			retStr += "HALT";
			break;
		case Instruction.NOP:
			retStr += "NOP";
			break;
		case Instruction.INVOKE:
			retStr += "INVOKE";
			break;
		case Instruction.RETURN:
			retStr += "RETURN";
			break;
		case Instruction.TRAMLABEL:
			retStr += "TRAMLABEL";
			break;
		case Instruction.TLCALLER:
			retStr += "TRAMLABELCALLER";
			break;
		case Instruction.AND:
			retStr += "AND";
			break;
		case Instruction.OR:
			retStr += "OR";
			break;
		default:
			retStr += "ERROR";
		}

		if (arg1 != null)
		{
			retStr += " " + arg1;

			if (arg2 != null)
			{
				retStr += " " + arg2;

				if (arg3 != null)
				{
					retStr += " " + arg3;
				}
			}
		}
		if (key != null)
		{
			retStr += " key = " + key;
		}
		if (exchangeSpot != null)
		{
			retStr += "  ersatzStelle = " + exchangeSpot;
		}
		if (arg4 != null)
		{
			retStr += "  " + arg4.toString();
		}

		return retStr;
	}
}