/*
 *  This file is part of "TweetyProject", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  TweetyProject is free software: you can redistribute it and/or modify
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
 *  Copyright 2016 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package org.tweetyproject.agents.dialogues.oppmodels;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tweetyproject.agents.Agent;
import org.tweetyproject.agents.Environment;
import org.tweetyproject.agents.Executable;
import org.tweetyproject.agents.Perceivable;
import org.tweetyproject.agents.dialogues.DialogueTrace;
import org.tweetyproject.agents.dialogues.ExecutableFormulaSet;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 * This class models the environment for agents in a game of deductive
 * argumentation. It consists of the universal propositional theory used
 * for argumentation (but not completely revealed to all agents) and
 * the current trace of disclosed sets of propositional formulas.
 * 
 * @author Matthias Thimm
 */
public class DeductiveEnvironment implements Environment, Perceivable {

	/** The current dialogue trace. */
	private DialogueTrace<PlFormula,Collection<PlFormula>> trace;
	/** The universal propositional theory used for argumentation. */
	//private PlBeliefSet universalTheory;
	
	/**
	 * Creates a new grounded environment.
	 * @param universalTheory the universal propositional theory used for argumentation.
	 */
	public DeductiveEnvironment(PlBeliefSet universalTheory){
		this.trace = new DialogueTrace<PlFormula,Collection<PlFormula>>();
		//this.universalTheory = universalTheory;
	}
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.agents.Environment#execute(org.tweetyproject.agents.Executable)
	 */
	@Override
	public Set<Perceivable> execute(Executable action) {
		if(!(action instanceof ExecutableFormulaSet))
			throw new IllegalArgumentException("Object of type ExecutableFormulaSet expected");
		this.trace.add((ExecutableFormulaSet)action);
		return this.getPercepts(null);
	}

	/* (non-Javadoc)
	 * @see org.tweetyproject.agents.Environment#execute(java.util.Collection)
	 */
	@Override
	public Set<Perceivable> execute(Collection<? extends Executable> actions) {
		for(Executable exec: actions){
			if(!(exec instanceof ExecutableFormulaSet))
				throw new IllegalArgumentException("Object of type ExecutableFormulaSet expected");
			this.trace.add((ExecutableFormulaSet)exec);
		}
		return this.getPercepts(null);
	}

	/* (non-Javadoc)
	 * @see org.tweetyproject.agents.Environment#getPercepts(org.tweetyproject.agents.Agent)
	 */
	@Override
	public Set<Perceivable> getPercepts(Agent agent) {
		//this environment is added as percept so that
		//the agent can inquire the necessary information
		//himself.
		Set<Perceivable> percepts = new HashSet<Perceivable>();
		percepts.add(this);
		return percepts;
	}
	
	/**
	 * Returns the current dialogue trace.
	 * @return the current dialogue trace.
	 */
	public DialogueTrace<PlFormula,Collection<PlFormula>> getDialogueTrace(){
		return this.trace;
	}
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.agents.Environment#reset()
	 */
	public boolean reset(){
		this.trace = new DialogueTrace<PlFormula,Collection<PlFormula>>();
		return true;
	}
}
