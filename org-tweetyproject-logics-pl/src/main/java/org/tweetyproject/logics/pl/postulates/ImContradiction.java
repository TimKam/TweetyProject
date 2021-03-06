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
 *  Copyright 2018 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package org.tweetyproject.logics.pl.postulates;

import java.util.Collection;
import java.util.Set;

import org.tweetyproject.commons.util.SetTools;
import org.tweetyproject.logics.commons.analysis.BeliefSetInconsistencyMeasure;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 * The "contradiction" postulate for inconsistency measures: A knowledge base
 * is maximally inconsistent if all non-empty subsets are inconsistent. 
 * This postulate is supposed to be an extension of the "normalization" postulate,
 * meaning 1 is the maximum inconsistency value.
 * 
 * @author Anna Gessler
 * @see org.tweetyproject.logics.pl.postulates.ImNormalization
 */
public class ImContradiction extends ImPostulate {
	
	/**
	 * Protected constructor so one uses only the single instance ImPostulate.CONTRADICTION
	 */
	protected ImContradiction() {		
	}
	
	@Override
	public String getName() {
		return "Contradiction";
	}

	@Override
	public boolean isApplicable(Collection<PlFormula> kb) {
		return true;
	}

	@Override
	public boolean isSatisfied(Collection<PlFormula> kb,
			BeliefSetInconsistencyMeasure<PlFormula> ev) {
		if(!this.isApplicable(kb))
			return true;
		double inconsistency = ev.inconsistencyMeasure(kb);
		Set<Set<PlFormula>> subsets =  new SetTools<PlFormula>().subsets(kb);
		SatSolver solver = SatSolver.getDefaultSolver();
		for (Set<PlFormula> kbx : subsets) {
			if (!kbx.isEmpty() && solver.isConsistent(kbx)) 
				return (inconsistency != 1);
		}
		return (inconsistency == 1);
	}

}
