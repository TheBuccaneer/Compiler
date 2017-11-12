package com.unitrier.st.uap;

public class AbstractMachine
{
	private int pc; 
	private int pp; 
	private int fp;
	private int top; 
	private Instruction[] prog;
	private boolean initialized;
	private boolean debug;
	private Stack<IntClass> stack = new Stack<IntClass>();

	public AbstractMachine(Instruction[] p)
	{
		this.pc = 0;
		this.pp = 0;
		this.fp = 0;
		this.top = -1;
		this.prog = p;
		this.initialized = true;
	}

	public AbstractMachine(int pp, int fp, int top)
	{
		this.pc = 0;
		this.pp = pp;
		this.fp = fp;
		this.top = top;

		for (int i = 0; i < top + 1; i++)
		{
			stack.push(top + 1, new IntClass(0));
		}
	}

	public AbstractMachine()
	{
		this.pc = 0;
		this.pp = 0;
		this.fp = 0;
		this.top = -1;
	}

	public void init(Instruction[] p)
	{
		prog = p;
		initialized = true;
	}

	public void exe()
	{
		if (!initialized)
		{
			System.out.println("Erst ein InstructionArray übergeben über Konstruktor oder der Methode init");
		}

		else
		{
			Instruction nextInstruction;

			while (pc >= 0)
			{

				nextInstruction = prog[pc];

				switch (nextInstruction.getOpcode())
				{
				case Instruction.CONST:
					CONST(nextInstruction.getArg1());
					break;
				case Instruction.STORE:
					store(nextInstruction.getArg1(), nextInstruction.getArg2());
					break;
				case Instruction.LOAD:
					load(nextInstruction.getArg1(), nextInstruction.getArg2());
					break;
				case Instruction.ADD:
					add();
					break;
				case Instruction.SUB:
					sub();
					break;
				case Instruction.MUL:
					mul();
					break;
				case Instruction.DIV:
					div();
					break;
				case Instruction.LT:
					lt();
					break;
				case Instruction.GT:
					gt();
					break;
				case Instruction.EQ:
					eq();
					break;
				case Instruction.NEQ:
					neq();
					break;
				case Instruction.IFZERO:
					ifzero(nextInstruction.getArg1());
					break;
				case Instruction.GOTO:
					GOTO(nextInstruction.getArg1());
					break;
				case Instruction.HALT:
					halt();
					break;
				case Instruction.NOP:
					nop();
					break;
				case Instruction.INVOKE:
					invoke(nextInstruction.getArg1(), nextInstruction.getArg2(), nextInstruction.getArg3());
					break;
				case Instruction.RETURN:
					RETURN();
					break;
				case Instruction.AND:
					and();
					break;
				case Instruction.OR:
					or();
					break;
				default:
					System.out.println("Ungültig " + pc);
				}

				if (debug)
				{
					StringBuilder deb = new StringBuilder();
					for (int i = 0; i <= top; i++)
					{
						deb.append(stack.get(i).val + ", ");
					}

					deb.append("\n oberstes Stackelement: ");
					if (stack.size() > 0)
					{
						deb.append(stack.get(top).val);
					}

					System.out.println("Stack: " + deb.toString() + ", Top = " + top + ", PC = " + pc + ", PP: " + pp
							+ ", FP: " + fp);
				}
			}
		}
	}

	private void CONST(int k)
	{
		stack.push(top + 1, new IntClass(k));
		top++;
		pc++;
	}

	private void store(int k, int d)
	{
		stack.push(spp(d, pp, fp) + k, stack.get(top));
		stack.remove(top);
		top--;
		pc++;
	}

	private void load(int k, int d)
	{
		stack.push(top + 1, stack.get(spp(d, pp, fp) + k));
		top++;
		pc++;
	}

	private int spp(int d, int pp, int fp)
	{
		if (d == 0)
		{
			return pp;
		} else
		{
			return (spp(d - 1, stack.get(fp + 2).val, stack.get(fp + 3).val));
		}
	}

	private int sfp(int d, int pp, int fp)
	{
		if (d == 0)
		{
			return fp;
		} else
		{
			return (sfp(d - 1, stack.get(fp + 2).val, stack.get(fp + 3).val));
		}
	}

	private void add()
	{
		stack.push(top - 1, new IntClass(stack.get(top - 1).val + stack.get(top).val));
		top--;
		pc++;
	}

	private void lt()
	{

		if (stack.get(top - 1).val < stack.get(top).val)
		{
			stack.push(top - 1, new IntClass(1));
		} else
		{
			stack.push(top - 1, new IntClass(0));
		}
		stack.remove(top);
		top--;
		pc++;
	}

	private void gt()
	{

		if (stack.get(top - 1).val > stack.get(top).val)
		{
			stack.push(top - 1, new IntClass(1));
		} else
		{
			stack.push(top - 1, new IntClass(0));
		}
		stack.remove(top);
		top--;
		pc++;
	}

	private void sub()
	{
		stack.push(top - 1, new IntClass(stack.get(top - 1).val - stack.get(top).val));
		stack.remove(top);
		top--;
		pc++;
	}

	private void mul()
	{
		stack.push(top - 1, new IntClass(stack.get(top - 1).val * stack.get(top).val));
		stack.remove(top);
		top--;
		pc++;
	}

	private void div()
	{
		stack.push(top - 1, new IntClass(stack.get(top - 1).val / stack.get(top).val));
		stack.remove(top);
		top--;
		pc++;
	}

	private void eq()
	{

		if (stack.get(top - 1).val == stack.get(top).val)
		{
			stack.push(top - 1, new IntClass(1));
		} else
		{
			stack.push(top - 1, new IntClass(0));
		}
		stack.remove(top);
		top--;
		pc++;
	}

	private void neq()
	{
		if (stack.get(top - 1).val != stack.get(top).val)
		{
			stack.push(top - 1, new IntClass(1));
		} else
		{
			stack.push(top - 1, new IntClass(0));
		}
		stack.remove(top);
		top--;
		pc++;
	}

	private void GOTO(int p)
	{
		pc = p;
	}

	private void ifzero(int p)
	{
		if (stack.get(top).val == 0)
		{
			pc = p;
		} else
		{
			pc++;
		}
		stack.remove(top);
		top--;
	}

	private void halt()
	{
		pc = -1;
	}

	private void nop()
	{
		pc++;
	}

	private void invoke(int n, int p, int d)
	{
		stack.push(top + 1, new IntClass(pp));
		stack.push(top + 2, new IntClass(fp));
		stack.push(top + 3, new IntClass(spp(d, pp, fp)));
		stack.push(top + 4, new IntClass(sfp(d, pp, fp)));
		stack.push(top + 5, new IntClass(pc + 1));
		pp = top - n + 1;
		fp = top + 1;
		top = top + 5;
		pc = p;
	}

	private void or()
	{
		if (stack.get(top - 1).val == 1 || stack.get(top).val == 1)
		{
			stack.get(top - 1).val = 1;
		} else
		{
			stack.get(top - 1).val = 0;
		}
		top = top - 1;
		pc = pc + 1;
	}

	private void and()
	{
		if (stack.get(top - 1).val == 1 && stack.get(top).val == 1)
		{
			stack.get(top - 1).val = 1;
		} else
		{
			stack.get(top - 1).val = 0;
		}
		top = top - 1;
		pc = pc + 1;
	}

	private void RETURN()
	{
		IntClass res = stack.get(top);
		top = pp;
		pp = stack.get(fp).val;
		pc = stack.get(fp + 4).val;
		fp = stack.get(fp + 1).val;
		stack.push(top, res);

		for (int i = stack.size() - 1; i > top; i--)
		{
			stack.remove(i);
		}
	}

	public int getResult()
	{
		return this.stack.get(this.top).val;
	}

	public void setDebug(boolean b)
	{
		this.debug = b;

	}

}
