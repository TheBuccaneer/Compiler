package com.unitrier.st.uap.aufgabe2.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerComponentNameProvider;
import org.jgrapht.ext.StringComponentNameProvider;

import com.unitrier.st.uap.Main;
import com.unitrier.st.uap.aufgabe2.graph.utility.CFG;
import com.unitrier.st.uap.aufgabe2.graph.utility.Edge;
import com.unitrier.st.uap.aufgabe2.graph.utility.Vertex;

public class Graph
{
	private CFG g = new CFG(Edge.class);

	public Graph()
	{
	}

	public void exportGraph()
	{
		IntegerComponentNameProvider<Vertex> p1 = new IntegerComponentNameProvider<Vertex>();
		StringComponentNameProvider<Vertex> p2 = new StringComponentNameProvider<Vertex>();
		StringComponentNameProvider<Edge> p3 = new StringComponentNameProvider<Edge>();
		DOTExporter<Vertex, Edge> exporter = new DOTExporter<Vertex, Edge>(p1, p2, p3, null, null);
		{
			try
			{
				exporter.exportGraph(g, new FileWriter(new File(Main.DOT_FILE_PATH_AND_NAME)));
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public void addVertex(Vertex v)
	{
		this.g.addVertex(v);

	}

	public void addEdge(Vertex source, Vertex target)
	{

		this.g.addEdge(source, target);

	}

	public CFG getGraph()
	{
		return this.g;
	}
}
