package com.unitrier.st.uap.aufgabe2.graph.utility;

import org.jgrapht.graph.DefaultDirectedGraph;

public class CFG extends DefaultDirectedGraph<Vertex, Edge> implements ICFG
{

	private static final long serialVersionUID = 1L;

	public CFG(Class<com.unitrier.st.uap.aufgabe2.graph.utility.Edge> class1)
	{
		super(class1);
	}

	private Vertex in, out;

	@Override
	public Vertex getIn()
	{

		return this.in;
	}

	@Override
	public Vertex getOut()
	{

		return this.out;
	}

	@Override
	public void setIn(Vertex in)
	{
		this.in = in;

	}

	@Override
	public void setOut(Vertex out)
	{
		this.out = out;
	}

	public Edge addEdge(Vertex v1, Vertex v2)
	{
		super.addEdge(v1, v2);
		return null;
	}

}
