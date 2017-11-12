package com.unitrier.st.uap.aufgabe2.graph.utility;

public class Vertex
{
	@SuppressWarnings("unused")
	private final String id;
	private String label;
	private String name;
	private static int counter = 0;

	private String color;
	private String shape;

	public Vertex(String label, String shape, String color)
	{
		this.name = label;
		// this.label = label + "\", shape=\"" + shape + "\", color=\"" + color;
		this.label = label;
		this.color = "\", color=\"" + color;
		this.shape = "\", shape=\"" + shape;
		this.id = "" + ++Vertex.counter;
	}

	public Vertex(String label, String shape)
	{
		this(label, shape, "black");
	}

	public Vertex(String label)
	{
		this(label, "box");
	}

	public String getName()
	{
		return this.name;
	}

	public String toString()
	{
		return this.label + shape + color;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof Vertex) && !(o instanceof DataAnalysisVertex))
			return false;
		return ((Vertex) o).getName().equals(this.label);
	}


}
