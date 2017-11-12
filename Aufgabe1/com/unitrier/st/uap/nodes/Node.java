package com.unitrier.st.uap.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import com.unitrier.st.uap.AddressPair;
import com.unitrier.st.uap.Instruction;
import com.unitrier.st.uap.TramLabel;

public abstract class Node
{
	public static int labelCount = 0;
	private String type;
	private Object attribute;
	private LinkedList<Node> children;
	protected static HashMap<Object, AddressPair> idsMap = new HashMap<Object, AddressPair>();

	public Node(String type)
	{
		this.type = type;
		this.attribute = null;
		this.children = new LinkedList<>();
	}

	public Node(String type, Object attribute)
	{
		this.type = type;
		this.attribute = attribute;
		this.children = new LinkedList<>();
	}

	public String getType()
	{
		return this.type;
	}

	public Object getAttribute()
	{
		return this.attribute;
	}

	public LinkedList<Node> getChildren()
	{
		return this.children;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}

	public void setChildren(LinkedList<Node> children)
	{
		this.children = children;
	}

	public void addChild(Node child)
	{
		this.children.add(child);
	}

	public void addChildren(LinkedList<Node> children)
	{
		this.children.addAll(children);
	}

	private String startTag()
	{
		String tag = "<" + type;

		if (attribute != null)
		{
			tag += " attr=\"" + attribute + "\"";
		}

		tag += ">";

		return tag;
	}

	private String endTag()
	{
		return "</" + type + ">";
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder(startTag());
		for (Node node : children)
		{
			str.append(node.toString());
		}
		str.append(endTag());
		return str.toString();
	}

	// ###################################################################
	// ###################################################################
	// ###################################################################
	// ################################My methods#########################

	/**
	 * 
	 * @param index
	 *            child index
	 * @return returns child at given index
	 */
	public Node getChildAt(int index)
	{
		return this.getChildren().get(index);
	}

	/**
	 * 
	 * @param nl
	 * @param rho
	 */
	public static void addLabel(int nl, HashMap<String, AddressPair> rho)
	{
		rho.put(Integer.toString(labelCount), new AddressPair(new TramLabel(-1), nl));
		labelCount++;
	}

	/**
	 * 
	 * @param type
	 * @return true if node has type
	 */

	public boolean equals(String type)
	{
		return this.getType().equals(type);
	}

	/**
	 * 
	 * @return attribute as String
	 */
	public String getAttributeToString()
	{
		return this.getAttribute().toString();
	}

	public String getChildAttributeAt(int index)
	{
		return this.getChildAt(index).getAttributeToString();
	}

	public abstract ArrayList<Instruction> code(int nl, HashMap<String, AddressPair> rho);
}
