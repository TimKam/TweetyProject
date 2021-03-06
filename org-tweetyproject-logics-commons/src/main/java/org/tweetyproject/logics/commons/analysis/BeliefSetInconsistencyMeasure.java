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
package org.tweetyproject.logics.commons.analysis;

import java.util.Collection;

import org.tweetyproject.commons.BeliefSet;
import org.tweetyproject.commons.Formula;
import org.tweetyproject.commons.postulates.PostulateEvaluatable;

/**
 * Classes extending this abstract class represent inconsistency measures
 * on belief sets.
 * 
 * @author Matthias Thimm
 * @param <S> The type of formulas this measure supports.
 */
public abstract class BeliefSetInconsistencyMeasure<S extends Formula> implements InconsistencyMeasure<BeliefSet<S,?>>, PostulateEvaluatable<S> {
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.logics.commons.analysis.InconsistencyMeasure#inconsistencyMeasure(org.tweetyproject.BeliefBase)
	 */
	public Double inconsistencyMeasure(BeliefSet<S,?> beliefBase){
		return this.inconsistencyMeasure((Collection<S>) beliefBase);
	}
	
	/**
	 * This method measures the inconsistency of the given set of formulas.
	 * @param formulas a collection of formulas.
	 * @return a Double indicating the degree of inconsistency.
	 */
	public abstract Double inconsistencyMeasure(Collection<S> formulas);
}
