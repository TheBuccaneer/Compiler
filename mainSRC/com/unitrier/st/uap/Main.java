package com.unitrier.st.uap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.unitrier.st.uap.a.StructImprover;
import com.unitrier.st.uap.nodes.Node;
import com.unitrier.st.uap.aufgabe2.graph.*;
import com.unitrier.st.uap.aufgabe4.DataAnalysis;
import com.unitrier.st.uap.b.CodeMaker;;

public class Main
{
	final public static String INPUT_FILE_PATH_AND_NAME = ".\\test\\test.txt";
	final public static String AST_PATH = "ast.xml";
	final public static String DOT_FILE_PATH_AND_NAME = "graph.dot";

	public static void main(String[] args)
	{

		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(AST_PATH))));)
		{

			// Projekt 2: enthalten in mainSCR
			Node ast = Main.readInAndMakeTree(INPUT_FILE_PATH_AND_NAME);
			// Projekt 3: Aufgabe 1a. Enthalten in Aufgabe1
			Main.makeStructureImprovement(ast);
			// Projekt 3: Aufgabe 2 und 3. Enthalten in Aufgabe2und3
			ast = Main.makeGraph(ast);
			// Projekt 3: Aufgabe 4
			ast = Main.dataAnalysis(ast);
			// Schreibt ast Node in xml
			Main.writeTreeInXML(ast, pw);
			// Projekt 3 Aufgabe 1b. Codeerzeugung. Läuft möglicherweise
			Main.generateCodeOfNode(ast);

		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Läuft nicht ganz korrekt und kann deshalb zu Fehlern bei der
	 * Codeerzeugung führen
	 * 
	 * @param ast
	 * @return
	 */
	private static Node dataAnalysis(Node ast)
	{
		GraphMaker gm = new GraphMaker();
		gm.makeGraph(ast);
		new DataAnalysis(gm.getGraph(), ast);
		gm = new GraphMaker();
		gm.makeGraph(ast);
		return ast;
	}

	/**
	 * Makes Graph
	 * 
	 * @param ast
	 * @return new node
	 */
	private static Node makeGraph(Node ast)
	{
		GraphMaker gm = new GraphMaker();
		gm.makeGraph(ast);
		return ast;
	}

	/**
	 * Generates Code und executes it. Here you can set debug true
	 * 
	 * @param ast
	 */
	private static void generateCodeOfNode(Node ast)
	{
		ArrayList<Instruction> program = new CodeMaker().generate(ast);
		Instruction[] prog = program.toArray(new Instruction[program.size()]);

		AbstractMachine abstractM = new AbstractMachine();
		abstractM.init(prog);
		// abstractM.setDebug(true);
		System.out.println("AM loaded.");
		abstractM.exe();
		System.out.println();
		System.out.println();
		System.out.println("Ergebnis der Aufgabe ist: " + abstractM.getResult());

	}

	/**
	 * AUfgabe 1a
	 * 
	 * @param ast
	 */
	private static void makeStructureImprovement(Node ast)
	{
		new StructImprover(ast);
	}

	private static Node readInAndMakeTree(String file) throws Exception
	{
		parser triplaParser = new parser(new Lexer(new FileReader(file)));
		return (((Node) (triplaParser.parse().value)));
	}

	public static void writeTreeInXML(Node ast, PrintWriter pw)
	{
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		pw.print(ast.toString());
		System.out.println("\"ast.xml\" file created");
	}
}
