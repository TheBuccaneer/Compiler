package com.unitrier.st.uap.aufgabe2.graph.utility;

import java.util.ArrayList;
import java.util.List;

import com.unitrier.st.uap.nodes.Node;

public class DataAnalysisVertex extends Vertex
{
	private ArrayList<Node> use = new ArrayList<Node>();
	private ArrayList<Node> def = new ArrayList<Node>();
	private ArrayList<Node> out = new ArrayList<Node>();
	private ArrayList<Node> in = new ArrayList<Node>();
	private Node node;

	public DataAnalysisVertex(String label, String shape, String color, Node n)
	{
		super(label, shape, color);
		this.node = n;
	}

	public DataAnalysisVertex(String label, String shape, Node n)
	{
		this(label, shape, "black", n);
	}

	public DataAnalysisVertex(String label, Node n)
	{
		this(label, "box", n);
	}

	public void addUse(ArrayList<Node> source)
	{
		addSourceListToTargetList(source, this.use);
	}

	public void addUse(Node v)
	{
		addNodeToTargetList(v, this.use);
	}

	public void addDef(ArrayList<Node> source)
	{
		addSourceListToTargetList(source, this.def);
	}

	public void addDef(Node v)
	{
		addNodeToTargetList(v, this.def);
	}

	public ArrayList<Node> getUse()
	{
		return this.use;
	}

	public ArrayList<Node> getDef()
	{
		return this.def;
	}

	public static List<Node> addSourceListToTargetList(List<Node> source, List<Node> target)
	{
		for (Node v : source)
			addNodeToTargetList(v, target);
		return target;
	}

	public static void addNodeToTargetList(Node v, List<Node> target)
	{
		if (!target.contains(v))
		{
			target.add(v);
		}
	}

	public Node getNode()
	{
		return this.node;
	}

	public ArrayList<Node> getOut()
	{
		return out;
	}

	public void setOut(ArrayList<Node> out)
	{
		this.out = out;
	}

	public ArrayList<Node> getIn()
	{
		return in;
	}

	public void setIn(ArrayList<Node> in)
	{
		this.in = in;
	}

}
