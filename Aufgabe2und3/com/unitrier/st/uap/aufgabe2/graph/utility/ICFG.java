package com.unitrier.st.uap.aufgabe2.graph.utility;

import org.jgrapht.DirectedGraph;

public interface ICFG extends DirectedGraph<Vertex, Edge>
{

	Vertex getIn();
	Vertex getOut();
	void setIn(Vertex in);
	void setOut(Vertex out);
	
}
