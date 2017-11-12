package com.unitrier.st.uap.aufgabe2.graph.utility;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge
{
	private Vertex target;
	private Vertex source;
	private String label = "";

	public void setTarget(Vertex tar)
	{
		this.target = tar;
	}

	public void setSource(Vertex src)
	{

		this.source = src;
	}

	public Vertex getTarget()
	{

		return this.target;
	}

	public Vertex getSource()
	{

		return this.source;
	}

	public Edge addEdge(Vertex v1, Vertex v2)
	{
		this.target = v2;
		this.source = v1;
		return this;
	}

	public Edge addEdge(Vertex v1, Vertex v2, String label)
	{

		this.target = v2;
		this.source = v1;
		this.label = label;
		return this;
	}

	public void setLabel(String label)
	{

		this.label = label;
	}

	public String toString()
	{
		return this.label;
	}

}
