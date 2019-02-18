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
package net.sf.tweety.logics.pl.postulates;

import java.util.Collection;
import java.util.Iterator;

import net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure;
import net.sf.tweety.logics.pl.sat.PlMusEnumerator;
import net.sf.tweety.logics.pl.syntax.PlBeliefSet;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;

/**
 * The "equal conflict" postulate for inconsistency measures: Minimal inconsistent subsets
 * of the same size should have the same inconsistency value.
 * 
 * @author Anna Gessler
 * @see net.sf.tweety.logics.pl.postulates.ImAttenuation
 */
public class ImEqualConflict extends ImPostulate{

	/**
	 * Protected constructor so one uses only the single instance ImPostulate.EQUALCONFLICT
	 */
	protected ImEqualConflict() {		
	}
	
	private PlBeliefSet mus1;
	private PlBeliefSet mus2;
	
	/* (non-Javadoc)
	 * @see net.sf.tweety.logics.pl.postulates.AbstractImPostulate#isApplicable(java.util.Collection)
	 */
	@Override
	public boolean isApplicable(Collection<PropositionalFormula> kb) {
		if(kb.isEmpty())
			return false;
		Collection<Collection<PropositionalFormula>> muses = PlMusEnumerator.getDefaultEnumerator().minimalInconsistentSubsets(kb);
		if (muses.size()<2) 
			return false;
		Iterator<Collection<PropositionalFormula>> it = muses.iterator();
		mus1 = new PlBeliefSet(it.next());
		mus2 = new PlBeliefSet(it.next());
		if (mus1.size() == mus2.size()) 
				return true;
		while (it.hasNext()) {
			mus2 = new PlBeliefSet(it.next());
			if (mus1.size() == mus2.size()) 
				return true; 
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.logics.pl.postulates.AbstractImPostulate#isSatisfied(java.util.Collection, net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure)
	 */
	@Override
	public boolean isSatisfied(Collection<PropositionalFormula> kb, BeliefSetInconsistencyMeasure<PropositionalFormula> ev) {
		if(!this.isApplicable(kb))
			return true;
		double inconsistency1 = ev.inconsistencyMeasure(mus1);
		double inconsistency2 = ev.inconsistencyMeasure(mus2);
		return (inconsistency1 == inconsistency2);
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.commons.postulates.Postulate#getName()
	 */
	public String getName() {
		return "Equal Conflict";
	}
}
