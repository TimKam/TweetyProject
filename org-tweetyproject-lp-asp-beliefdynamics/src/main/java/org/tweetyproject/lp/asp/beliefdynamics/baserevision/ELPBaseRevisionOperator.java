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
package org.tweetyproject.lp.asp.beliefdynamics.baserevision;

import java.util.Collection;

import org.tweetyproject.beliefdynamics.MultipleBaseRevisionOperator;
import org.tweetyproject.lp.asp.reasoner.ASPSolver;
import org.tweetyproject.lp.asp.syntax.Program;
import org.tweetyproject.lp.asp.syntax.ASPRule;

/**
 * This class implements the base revision operator for extended
 * logic programs as introduced in [KKI12]. The revision of a set
 * of rules is defined as the screened maxi-choice consolidation of the
 * union of the belief base and the new beliefs.
 * 
 *  [KKI12] Kruempelmann, Patrick und Gabriele Kern-Isberner: 
 * 	Belief Base Change Operations for Answer Set Programming. 
 *  In: Cerro, Luis Farinas, Andreas Herzig und Jerome Mengin (Herausgeber):
 *  Proceedings of the 13th European conference on Logics in Artificial 
 *  Intelligence, Band 7519, Seiten 294-306, Toulouse, France, 2012. 
 *  Springer Berlin Heidelberg.
 *  
 * @author Sebastian Homann
 */
public class ELPBaseRevisionOperator extends MultipleBaseRevisionOperator<ASPRule> {
	private ASPSolver solver;
	private SelectionFunction<ASPRule> selection;
	
	public ELPBaseRevisionOperator(ASPSolver solver, SelectionFunction<ASPRule> selection) {
		this.solver = solver;
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * @see org.tweetyproject.beliefdynamics.MultipleBaseRevisionOperator#revise(java.util.Collection, java.util.Collection)
	 */
	@Override
	public Collection<ASPRule> revise(Collection<ASPRule> base,
			Collection<ASPRule> formulas) {
		Program newKnowledge = new Program(formulas);		
		
		ScreenedMaxichoiceConsolidation consolidationOperator = new ScreenedMaxichoiceConsolidation(newKnowledge, selection, solver);
		Program union = new Program();
		union.addAll(base);
		union.addAll(formulas);
		Program result;
		try {
			result = consolidationOperator.consolidate(union);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
