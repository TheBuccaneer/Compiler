package com.unitrier.st.uap.a;

import com.unitrier.st.uap.nodes.Node;

public class StructImprover
{

	public StructImprover(Node ast)
	{
		this.improve(ast);
	}

	private void improve(Node ast)
	{
		String type = ast.getType();

		for (int i = 0; i < ast.getChildren().size(); i++)
		{
			Node child = ast.getChildren().get(i);
			if (type.equals("SEMI") || type.equals("DEF") || type.equals("ARGS") || type.equals("PARAMS"))
			{
				if (child.getType().equals(type))
				{
					System.out.println("StructImprover - found double child node " + type);
					ast.getChildren().addAll(i + 1, child.getChildren());
					ast.getChildren().remove(i--);
				}
			}

		}
		for (Node child : ast.getChildren())
			improve(child);
	}

}
