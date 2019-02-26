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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure;
import net.sf.tweety.logics.pl.reasoner.SatReasoner;
import net.sf.tweety.logics.pl.syntax.PlBeliefSet;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;

/**
 * The "irrelevance of syntax" postulate for inconsistency measures: Knowledge
 * bases with pairwise equivalent formulas should receive the same inconsistency
 * value.
 * 
 * @author Anna Gessler
 */
public class ImIrrelevanceOfSyntax extends ImPostulate {

	/**
	 * Protected constructor so one uses only the single instance
	 * ImPostulate.IRRELEVANCEOFSYNTAX
	 */
	protected ImIrrelevanceOfSyntax() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.tweety.logics.pl.postulates.AbstractImPostulate#isApplicable(java.util
	 * .Collection)
	 */
	@Override
	public boolean isApplicable(Collection<PropositionalFormula> kb) {
		if (kb.isEmpty())
			return false;
		List<PropositionalFormula> orderedKB = ((PlBeliefSet) kb).getCanonicalOrdering();
		List<PropositionalFormula> left = new ArrayList<PropositionalFormula>(
				orderedKB.subList(0, orderedKB.size() / 2));
		List<PropositionalFormula> right = new ArrayList<PropositionalFormula>(
				orderedKB.subList(orderedKB.size() / 2, orderedKB.size()));
		SatReasoner reasoner = new SatReasoner();
		for (PropositionalFormula l : left) {
			Boolean found = false;
			for (PropositionalFormula r : right) {
				if (reasoner.isEquivalent(l, r)) {
					found = true;
					right.remove(r);
					continue;
				}

			}
			if (!found)
				return false;
			else
				found = false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.tweety.logics.pl.postulates.AbstractImPostulate#isSatisfied(java.util.
	 * Collection,
	 * net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure)
	 */
	@Override
	public boolean isSatisfied(Collection<PropositionalFormula> kb,
			BeliefSetInconsistencyMeasure<PropositionalFormula> ev) {
		if (!this.isApplicable(kb))
			return true;
		List<PropositionalFormula> orderedKB = ((PlBeliefSet) kb).getCanonicalOrdering();
		List<PropositionalFormula> left = new ArrayList<PropositionalFormula>(
				orderedKB.subList(0, orderedKB.size() / 2));
		List<PropositionalFormula> right = new ArrayList<PropositionalFormula>(
				orderedKB.subList(orderedKB.size() / 2, orderedKB.size()));
		double inconsistency1 = ev.inconsistencyMeasure(left);
		double inconsistency2 = ev.inconsistencyMeasure(right);
		return (inconsistency1 == inconsistency2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.tweety.commons.postulates.Postulate#getName()
	 */
	public String getName() {
		return "Irrelevance of Syntax";
	}
}
