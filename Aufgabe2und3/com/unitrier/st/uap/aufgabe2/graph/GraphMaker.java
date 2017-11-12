package com.unitrier.st.uap.aufgabe2.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graphs;
import com.unitrier.st.uap.aufgabe2.graph.utility.*;
import com.unitrier.st.uap.nodes.Node;

/**
 * 
 * @author Tihomir Bicanic
 *
 */

/**
 * 
 * class generates graph
 *
 */
public class GraphMaker
{
	private Graph g;
	private Vertex in;
	private Vertex out;

	public GraphMaker()
	{
		this.g = new Graph();
		this.in = new DataAnalysisVertex("in", "doublecircle", null);
		this.out = new DataAnalysisVertex("out", "doublecircle", null);
		g.getGraph().setIn(this.in);
		g.getGraph().setOut(this.out);
		this.g.addVertex(this.in);
		this.g.addVertex(this.out);
	}

	/**
	 * traverses over given ast-node
	 * 
	 * @param n
	 *            actual node
	 * @param pred
	 *            predecessor of next Vertex
	 * @param rho
	 *            address Environment
	 * @param nl
	 *            nested level
	 * @return
	 */
	private List<Vertex> traverse(Node n, List<Vertex> pred, Map<String, FuncVertex> rho, int nl)
	{
		if (n.equals("LET"))
			this.let(n, pred, rho, nl);
		else if (n.equals("DEF"))
			this.def(n, pred, rho, nl);
		else if (n.equals("FUNC"))
			this.func(n, pred, rho, nl);
		else if (n.equals("READ"))
			this.read(n, pred, rho, nl);
		else if (n.equals("CALL"))
			this.call(n, pred, rho, nl);
		else if (n.equals("CONST"))
			this.cons(n, pred, rho, nl);
		else if (n.equals("ASSIGN"))
			this.assign(n, pred, rho, nl);
		else if (n.equals("OP"))
			this.op(n, pred, rho, nl);
		else if (n.equals("IF"))
			this.ifFunc(n, pred, rho, nl);
		else if (n.equals("WHILE"))
			this.whileFunc(n, pred, rho, nl);
		else
			for (Node child : n.getChildren())
				this.traverse(child, pred, rho, nl);

		return pred;
	}

	/**
	 * used to creat while and if condition and adding use-set to
	 * DataAnalysisVertex
	 * 
	 * @param n
	 *            actual node
	 * @param vorganger
	 *            pre list
	 * @param rho
	 *            address environment
	 * @param nl
	 *            nestedlevel
	 * @return new created DataAnalysisVertex
	 */
	private DataAnalysisVertex condition(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{

		// oversteps condition node and returns par Node
		Node par = n.getChildAt(0).getChildAt(0);
		// use List
		ArrayList<Node> used = new ArrayList<Node>();
		// gets if and while Condition as String back
		String condition = this.getOpString(true, par, vorganger, rho, used, nl);
		// Vertex for String condition
		DataAnalysisVertex conditionVertex = new DataAnalysisVertex(condition, "diamond", n);
		System.out.println("CONDITION " + used.size());
		conditionVertex.addUse(used);
		this.addVertexThanEdgeThanClearAndAdd(vorganger, conditionVertex);
		return conditionVertex;
	}

	/**
	 * creates while structure and vertex
	 * 
	 */
	private void whileFunc(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		// Erster Knoten vor dem do while
		Vertex direkterVorgangerimGraph = vorganger.get(0);
		// Abarbeitung von do
		traverse(n.getChildAt(1), vorganger, rho, nl);
		// falls mehrere Pfade im do bestehen (then else, werden beide
		// gebraucht)
		List<Vertex> temp = new ArrayList<Vertex>(vorganger);
		// erster Pfard wird schon hier hinzugefügt
		DataAnalysisVertex conditionVertex = this.condition(n, vorganger, rho, nl);

		// Falls zweiter Pfad vorhanden, füge auch diesen hinzu
		if (temp.size() > 1)
		{
			g.addEdge(temp.get(1), conditionVertex);
		}
		direkterVorgangerimGraph = this.getWhile(this.g.getGraph(), conditionVertex, direkterVorgangerimGraph);
		g.addEdge(conditionVertex, direkterVorgangerimGraph);
		System.out.println(g.getGraph().outgoingEdgesOf(conditionVertex).size());
		this.setLabel(conditionVertex);

	}

	/*
	 * Method gives back first do-node or the node before first do-node, if
	 * error occurs
	 */
	private Vertex getWhile(final CFG g, Vertex condVer, Vertex directPred)
	{
		int counter = 0;
		Vertex tmp = directPred;
		directPred = this.getWhileBeginning(this.g.getGraph(), condVer, directPred, false, counter);
		boolean isChild = false;
		for (Vertex child : Graphs.successorListOf(this.g.getGraph(), tmp))
			if (child.equals(directPred))
			{
				isChild = true;
			}
		if (directPred == null || !isChild)
			return tmp;
		return directPred;
	}

	/**
	 * 
	 * gives back do node
	 */
	private Vertex getWhileBeginning(final CFG g, Vertex conditionVertex, Vertex direkterVorgangerimGraph, boolean b,
			int counter)
	{
		if (b)
			return direkterVorgangerimGraph;

		// in case of method stucks in graph
		if (counter++ > 1000)
			return direkterVorgangerimGraph;

		List<Vertex> list = Graphs.predecessorListOf(g, conditionVertex);
		for (Vertex pred : list)
		{

			if (pred.equals(direkterVorgangerimGraph) && !b)
			{
				b = false;
				direkterVorgangerimGraph = conditionVertex;
				return direkterVorgangerimGraph;
			}
			direkterVorgangerimGraph = this.getWhileBeginning(g, pred, direkterVorgangerimGraph, b, counter);
		}

		return direkterVorgangerimGraph;
	}

	/*
	 * generates if condition and then/else structure
	 */
	private void ifFunc(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{

		DataAnalysisVertex conditionVertex = this.condition(n, vorganger, rho, nl);
		vorganger.clear();
		vorganger.addAll(this.thenFunc(conditionVertex, n, rho, nl));
		vorganger.addAll(this.elseFunc(conditionVertex, n, rho, nl));
		this.setLabel(conditionVertex);

	}

	private void setLabel(DataAnalysisVertex conditionVertex)
	{
		List<Vertex> edges = Graphs.successorListOf(this.g.getGraph(), conditionVertex);
		if (edges.size() > 0)
		{
			Edge e1 = this.g.getGraph().getEdge(conditionVertex, edges.get(0));
			e1.setLabel("T");
			if (edges.size() == 1)
				return;
			Edge e2 = this.g.getGraph().getEdge(conditionVertex, edges.get(1));
			e2.setLabel("F");
		}

	}

	/*
	 * creates then
	 */
	private List<Vertex> thenFunc(Vertex condition, Node n, Map<String, FuncVertex> rho, int nl)
	{
		ArrayList<Vertex> tempList = new ArrayList<Vertex>();
		tempList.add(condition);
		return traverse(n.getChildAt(1), tempList, rho, 0);
	}

	/*
	 * creates else
	 */
	private List<Vertex> elseFunc(Vertex condition, Node n, Map<String, FuncVertex> rho, int nl)
	{
		ArrayList<Vertex> tempList = new ArrayList<Vertex>();
		tempList.add(condition);
		return traverse(n.getChildAt(2), tempList, rho, 0);

	}

	/*
	 * creates stand alone op withou assign
	 */
	private void op(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		String opName = this.getOpString(true, n, vorganger, rho, new ArrayList<Node>(), nl);
		Vertex op = new Vertex(opName);
		this.addVertexThanEdgeThanClearAndAdd(vorganger, op);
	}

	/*
	 * creates assign vertex
	 */
	private void assign(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		// Zuweisungsvariable
		String id = n.getChildAttributeAt(0);
		// Def und Use Menge
		ArrayList<Node> used = new ArrayList<Node>();

		String expr = this.getOpString(true, n.getChildAt(1), vorganger, rho, used, nl);
		DataAnalysisVertex assign = new DataAnalysisVertex(id + "= " + expr, n);
		assign.addUse(used);
		assign.addDef(n.getChildAt(0));
		this.addVertexThanEdgeThanClearAndAdd(vorganger, assign);

	}

	private void cons(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		String attribute = n.getAttributeToString();

		Vertex tmp = new Vertex(attribute);
		this.addVertexThanEdgeThanClearAndAdd(vorganger, tmp);

	}

	/*
	 * invoked if function is declared
	 */
	private void defCall(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		if (nl == 1)
		{
			vorganger.clear();
			vorganger.add(this.in);
		}
		// Falls erster Aufruf, dann setze in Knoten davor

		String name = n.getChildAttributeAt(0);

		// Call f(2) bei Funktionsnamen f und Argument 2
		String callLabel = "CALL " + name + "(";
		// Geht durch die Argumente durch, fügt diese dem Graphen hinzu und füge
		// Strings zum callLabel hinzu
		for (Node args : n.getChildAt(1).getChildren())
		{
			this.traverse(args, vorganger, rho, nl);
			callLabel += vorganger.get(0).getName() + ", ";
		}
		callLabel = callLabel.substring(0, callLabel.length() - 2) + ")";

		FuncVertex fv = new FuncVertex(g);
		rho.put(name, fv);
		fv.setCallVertex(callLabel);
		this.g.addEdge(vorganger.get(0), fv.getCallVertex());

		this.clearAndAdd(vorganger, fv.getCallVertex());

	}

	/*
	 * invoked if already declared function is called
	 */
	private String call(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{

		// Name der Funktion
		String functionName = n.getChildAttributeAt(0);
		System.out.println(functionName);
		String callFunctionName = "CALL " + functionName + "(";
		FuncVertex fv = rho.get(functionName);

		// Argumente werden durchlaufen, in cons als Vertex angelegt, dem
		// Graphen hinzugefügt und mit getName an callString angefügt
		for (Node args : n.getChildAt(1).getChildren())
		{
			callFunctionName += this.traverse(args, vorganger, rho, nl).get(0).getName() + ", ";
		}
		callFunctionName = callFunctionName.substring(0, callFunctionName.length() - 2) + ")";
		// Der Call Vertex für den Aufruf einer bereits deklarierten Funktion
		Vertex callVertex = new Vertex(callFunctionName, "octagon", fv.getColor());
		// Der dazugehörige Return knoten
		Vertex retVertex = new Vertex(callFunctionName.replace("CALL", "RET"), "octagon", fv.getColor());
		g.addVertex(callVertex);
		g.addVertex(retVertex);
		// Vorgänger des Call aufsrufs wird geedget
		g.addEdge(vorganger.get(0), callVertex);
		g.addEdge(callVertex, retVertex);
		g.addEdge(callVertex, fv.getStartVertex());
		g.addEdge(rho.get(functionName).getEndVertex(), retVertex);
		this.clearAndAdd(vorganger, retVertex);

		return callFunctionName;

	}

	private void let(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		Map<String, FuncVertex> rho2 = new HashMap<String, FuncVertex>(rho);
		Node bodyChild = n.getChildAt(1).getChildAt(0);
		if (bodyChild.equals("CALL"))
		{
			this.defCall(bodyChild, vorganger, rho2, nl + 1);
		} else
		{
			for (Node calls : bodyChild.getChildren())
			{
				this.defCall(calls, vorganger, rho2, nl + 1);
			}

		}

		this.traverse(n.getChildAt(0), vorganger, rho2, nl + 1);
	}

	private void read(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		DataAnalysisVertex read = new DataAnalysisVertex(n.getChildAttributeAt(0), n);
		read.addUse(n.getChildAt(0));
		this.addVertexThanEdgeThanClearAndAdd(vorganger, read);
	}

	private void def(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		for (Node child : n.getChildren())
		{
			/*
			 * TODO: Falls Probleme auftretgen, dann wieder nl + 1
			 */
			this.traverse(child, vorganger, rho, nl);
		}
	}

	/*
	 * called id node is func
	 */
	private void func(Node n, List<Vertex> vorganger, Map<String, FuncVertex> rho, int nl)
	{
		String fullFunctionName = this.makeParas(n);
		FuncVertex fv = rho.get(n.getChildAttributeAt(0));
		fv.setStartVertex("START " + fullFunctionName);
		fv.setEndVertex("END " + fullFunctionName);
		fv.setRetVertex("RET " + fullFunctionName);

		if (nl == 1)
		{
			vorganger.clear();
			vorganger.add(fv.getCallVertex());
		}

		g.addEdge(vorganger.get(0), fv.getStartVertex());

		this.g.addEdge(fv.getCallVertex(), fv.getStartVertex());
		this.g.addEdge(fv.getCallVertex(), fv.getRetVertex());
		this.g.addEdge(fv.getEndVertex(), fv.getRetVertex());

		this.clearAndAdd(vorganger, fv.getStartVertex());
		this.traverse(n.getChildAt(2), vorganger, rho, nl);

		for (Vertex preds : vorganger)
		{
			g.addEdge(preds, fv.getEndVertex());
		}

		if (nl == 1)
			g.addEdge(fv.getRetVertex(), this.out);
		this.clearAndAdd(vorganger, fv.getRetVertex());

	}

	/*
	 * clears predecessor and makes vertex new predecessor
	 */
	private void clearAndAdd(List<Vertex> pred, Vertex n)
	{
		pred.clear();
		pred.add(n);
	}

	/*
	 * reads out parameters and gives them back as string
	 */
	private String makeParas(Node n)
	{
		String startLabel = n.getChildAttributeAt(0) + "(";
		for (Node params : n.getChildAt(1).getChildren())
		{
			startLabel += params.getAttributeToString() + ", ";
		}
		startLabel = startLabel.substring(0, startLabel.length() - 2);
		startLabel += ")";

		return startLabel;
	}

	/*
	 * init method
	 */
	public void makeGraph(Node ast)
	{
		this.traverse(ast, new ArrayList<Vertex>(), new HashMap<String, FuncVertex>(), 0);
		g.exportGraph();
	}

	/**
	 * 
	 * @param needed
	 *            is it needes to add read node to list?
	 * @param n
	 *            actual node
	 * @param pred
	 *            predecessor
	 * @param rho
	 *            address environment
	 * @param list
	 *            used as use list
	 * @param nl
	 *            nested level
	 * @return
	 */
	private String getOpString(boolean needed, Node n, List<Vertex> pred, Map<String, FuncVertex> rho,
			ArrayList<Node> list, int nl)
	{
		if (n.equals("READ"))
		{
			String id = n.getChildAttributeAt(0);
			if (needed)
			{
				Node child = n.getChildAt(0);
				if (!list.contains(child))
				{
					this.traverse(n, pred, rho, nl).get(0);
					list.add(child);
				}
			}
			return id;
		} else if (n.equals("CONST"))
		{
			return n.getAttributeToString();
		} else if (n.equals("PAR"))
			return "(" + getOpString(needed, n.getChildAt(0), pred, rho, list, nl) + ")";
		else if (n.equals("EXPR"))
			return getOpString(needed, n.getChildAt(0), pred, rho, list, nl);
		else if (n.equals("OP"))
			return getOpString(needed, n.getChildAt(0), pred, rho, list, nl) + n.getAttributeToString()
					+ getOpString(needed, n.getChildAt(1), pred, rho, list, nl);
		else if (n.equals("CALL"))
		{
			String callString = this.call(n, pred, rho, nl).replace("CALL", "");
			return callString;
		} else
			return "Kein solcher Typ " + n.getType();
	}

	private void addVertexThanEdgeThanClearAndAdd(List<Vertex> vorganger, Vertex pred)
	{
		g.addVertex(pred);
		g.addEdge(vorganger.get(0), pred);
		this.clearAndAdd(vorganger, pred);
	}

	public CFG getGraph()
	{
		return this.g.getGraph();
	}

}
