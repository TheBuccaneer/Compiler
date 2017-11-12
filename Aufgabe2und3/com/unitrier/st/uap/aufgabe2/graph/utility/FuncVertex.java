package com.unitrier.st.uap.aufgabe2.graph.utility;

import com.unitrier.st.uap.aufgabe2.graph.Graph;
import com.unitrier.st.uap.aufgabe2.graph.utility.Vertex;

public class FuncVertex
{
	private Vertex startVertex;
	private Vertex endVertex;
	private Vertex callVertex;
	private Vertex retVertex;
	private Graph g;
	private String color;

	private static int counter = 0;
	private static String[] COLORS =
	{ "blue", "green", "red", "yellow", "deeppink1", "lightslateblue" };

	public FuncVertex(Graph g)
	{
		this.g = g;
		this.color = "\", color=\"" + COLORS[counter++];
		counter %= COLORS.length;
	}

	public String getColor()
	{
		return this.color;
	}

	public Vertex getStartVertex()
	{
		return startVertex;
	}

	public void setStartVertex(String vertexName)
	{
		if (this.startVertex == null)
		{
			this.startVertex = new Vertex(vertexName, "octagon", this.getColor());
			this.g.addVertex(this.startVertex);
		}
	}

	public Vertex getEndVertex()
	{
		return endVertex;
	}

	public void setEndVertex(String endVertex)
	{
		if (this.endVertex == null)
		{
			this.endVertex = new DataAnalysisVertex(endVertex, "octagon", this.getColor(), null);
			this.g.addVertex(this.endVertex);
		}
	}

	public Vertex getCallVertex()
	{
		return callVertex;
	}

	public void setCallVertex(String callString)
	{
		if (this.callVertex == null)
		{
			this.callVertex = new Vertex(callString, "octagon", this.getColor());
			this.g.addVertex(this.callVertex);
		}
	}

	public Vertex getRetVertex()
	{
		return retVertex;
	}

	public void setRetVertex(String retVertex)
	{
		if (this.retVertex == null)
		{
			this.retVertex = new Vertex(retVertex, "octagon", this.getColor());
			this.g.addVertex(this.retVertex);
		}
	}
}
