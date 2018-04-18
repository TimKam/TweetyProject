/*
 *  This file is part of "Tweety", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  Tweety is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2018 The Tweety Project Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.logics.ml.reasoner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import net.sf.tweety.commons.Answer;
import net.sf.tweety.commons.BeliefBase;
import net.sf.tweety.commons.Formula;
import net.sf.tweety.commons.util.Shell;
import net.sf.tweety.logics.commons.syntax.RelationalFormula;
import net.sf.tweety.logics.fol.FolBeliefSet;
import net.sf.tweety.logics.ml.writer.SPASSWriter;
import net.sf.tweety.logics.ml.ModalBeliefSet;

/**
 * 
 * Invokes SPASS (<a href="http://www.mpi-inf.mpg.de/departments/automation-of-logic/software/spass-workbench/"
 * >http://www.mpi-inf.mpg.de/departments/automation-of-logic/software/spass-workbench/</a>), 
 * an automated theorem prover for first-order logic, modal logic and description logics.
 * 
 * @author Anna Gessler
 *
 */
public class SPASS extends ModalReasoner {
	/**
	 * String representation of the SPASS path.
	 */
	private String binaryLocation;
	
	/**
	 * Shell to run SPASS.
	 */
	private Shell bash;
	
	/**
	 * Command line options that will be used by SPASS when executing the query.
	 * The default value disables most outputs except for the result and enables
	 * TPTP input format instead of SPASS input format.
	 * */
	private String cmdOptions = "-PGiven=0 -PProblem=0"; 

	/**
	 * Constructs a new instance pointing to a specific SPASS Prover.
	 * 
	 * @param binaryLocation
	 *            of the SPASS executable on the hard drive
	 * @param bash
	 *            shell to run commands
	 */
	public SPASS(String binaryLocation, Shell bash) {
		super(new FolBeliefSet());
		this.binaryLocation = binaryLocation;
		this.bash = bash;
	}
	
	/**
	 * Constructs a new instance pointing to a specific SPASS.
	 * 
	 * @param kb 
	 * 			  a knowledge base
	 * @param binaryLocation
	 *            of the SPASS executable on the hard drive
	 * @param bash
	 *            shell to run commands
	 */
	public SPASS(BeliefBase kb, String binaryLocation, Shell bash) {
		super(kb);
		this.binaryLocation = binaryLocation;
		this.bash = bash;
	}
	
	/**
	 * Constructs a new instance pointing to a specific SPASS.
	 * 
	 * @param kb 
	 * 			  a knowledge base
	 * @param binaryLocation
	 *            of the SPASS executable on the hard drive
	 * @param bash
	 *            shell to run commands
	 */
	public SPASS(BeliefBase kb, String binaryLocation) {
		super(kb);
		this.binaryLocation = binaryLocation;
		this.bash = Shell.getNativeShell();
	}
	
	/**
	 * Constructs a new instance pointing to a specific SPASS
	 * 
	 * @param binaryLocation
	 *            of the SPASS executable on the hard drive
	 */
	public SPASS(String binaryLocation) {
		this(binaryLocation, Shell.getNativeShell());
	}
	
	/**
	 * Sets the command line options that will be used by SPASS when executing the query.
	 * @param s a string containing the command line arguments
	 */
	public void setCmdOptions(String s){
		this.cmdOptions = s;
	}
	
	@Override
	public Answer query(Formula query) {
		ModalBeliefSet kb =  (ModalBeliefSet) this.getKnowledgeBase();
		Answer answer = new Answer(kb,query);
		String output = null;
		try {
			File file = File.createTempFile("tmp", ".txt");
			file.deleteOnExit();
			SPASSWriter writer = new SPASSWriter(new PrintWriter(file));
			writer.printProblem(kb, (RelationalFormula) query);
			writer.close();
			
			String cmd = binaryLocation + " " + cmdOptions + " " + file.getAbsolutePath().replaceAll("\\\\", "/");
			output = bash.run(cmd);
			if (evaluateResult(output)) 
				answer.setAnswer(true);
			else 
				answer.setAnswer(false);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
	
	/**
	 * Determines the answer wrt. to the given query and returns the proof (if applicable).
	 * May decrease SPASS's performance, use {@link net.sf.tweety.logics.ml.reasoner.SPASS#query(Formula)}
	 * if only a yes/no result is needed.
	 * 
	 * @param query a formula
	 * @return a string containing proof documentation 
	 */
	public String queryProof(Formula query) {
		ModalBeliefSet kb = (ModalBeliefSet) this.getKnowledgeBase();
		String output = null;
		try {
			File file = File.createTempFile("tmp", ".txt");	
			SPASSWriter writer = new SPASSWriter(new PrintWriter(file));
			writer.printProblem(kb, (RelationalFormula) query);
			writer.close();
			
			//Run query with option to document proofs
			String cmd = binaryLocation + " " + cmdOptions + " -DocProof" + " " + file.getAbsolutePath().replaceAll("\\\\", "/");
			output = bash.run(cmd);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		int i = output.indexOf("Here is a proof with");
		if (i==-1) 
			return "No proof found.";
		return output.substring(i);
	}
	
	/**
	 * Evaluates SPASS results.
	 * 
	 * @param output
	 *            of a SPASS query
	 * @return true if a proof was found, false otherwise
	 */
	private boolean evaluateResult(String output) {
		if (Pattern.compile("SPASS beiseite: Proof found").matcher(output).find())
			return true;
		if (Pattern.compile("SPASS beiseite: Completion found").matcher(output).find())
				return false;
		if (Pattern.compile("SPASS beiseite: Ran out of time").matcher(output).find())
			throw new RuntimeException("Failure: SPASS timeout.");
		throw new RuntimeException("Failure: SPASS returned no result which can be interpreted.");
	}

}
