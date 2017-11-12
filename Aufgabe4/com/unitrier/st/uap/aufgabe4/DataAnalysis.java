package com.unitrier.st.uap.aufgabe4;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.traverse.DepthFirstIterator;

import com.unitrier.st.uap.aufgabe2.graph.utility.CFG;
import com.unitrier.st.uap.aufgabe2.graph.utility.DataAnalysisVertex;
import com.unitrier.st.uap.aufgabe2.graph.utility.Edge;
import com.unitrier.st.uap.aufgabe2.graph.utility.Vertex;
import com.unitrier.st.uap.nodes.Node;

public class DataAnalysis
{

	private CFG g;
	private Node root;

	public DataAnalysis(CFG g2, Node root)
	{
		this.root = root;
		this.g = g2;

		this.findeLive(g);
	}

	private void findeLive(CFG g2)
	{
		int nl = 0;

		DepthFirstIterator<Vertex, Edge> it = new DepthFirstIterator<Vertex, Edge>(g);
		while (it.hasNext())
		{
			Vertex x = it.next();

			if (x instanceof DataAnalysisVertex)
			{
				DataAnalysisVertex dv = (DataAnalysisVertex) x;
				if (!dv.getDef().isEmpty())
				{
					boolean b = this.canBeDeleted(dv, dv.getDef().get(0), true, ++nl);

					if (b)
					{
						this.deleteNode(dv.getNode(), this.root, true);

					}
				}
			}
		}

	}

	private boolean canBeDeleted(Vertex dv, final Node n, boolean b, int nl)
	{
		if (!b)
			return b;

		// Nachfolger des aktuellen Knotens mit einem def
		List<Vertex> list = Graphs.successorListOf(this.g, dv);
		for (Vertex child : list)
		{
			// Falls Nachfolger relevant
			if (child instanceof DataAnalysisVertex)
			{
				DataAnalysisVertex d = (DataAnalysisVertex) child;

				d.getOut().addAll(d.getIn());
				d.getIn().addAll(d.getOut());
				d.getIn().removeAll(d.getDef());
				d.getIn().addAll(d.getUse());

				String nName = n.getAttributeToString();
				for (Node use : d.getUse())
				{
					String useName = use.getAttributeToString();

					if (useName.equals(nName))
					{

						b = false;
						return b;
					}
				}

				if (!d.getDef().isEmpty() && d.getDef().get(0).getAttributeToString().equals(nName))
				{
					System.out.println("HAHA");
					b = true;
					return b;
				}
			}
			b = this.canBeDeleted(child, n, b, nl);

		}
		return b;
	}

	private void deleteNode(final Node gesuchteNode, Node aktuelleNode, boolean b)
	{
		if (b)
		{
			List<Node> children = aktuelleNode.getChildren();
			for (int i = 0; i < children.size(); i++)
			{
				Node child = children.get(i);

				if (child.equals(gesuchteNode) && b)
				{
					b = false;
					Node tmp = child.getChildAt(1);
					children.remove(i);
					children.add(i, tmp);
					return;
				}
				this.deleteNode(gesuchteNode, child, b);
			}
		}

	}
}
