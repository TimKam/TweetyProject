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
package org.tweetyproject.logics.pl.sat;

import java.util.Collection;

import org.tweetyproject.logics.commons.analysis.AbstractMusEnumerator;
import org.tweetyproject.logics.commons.analysis.NaiveMusEnumerator;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 * This abstract class models a MUS enumerator for propositional logic, i.e. an approach
 * that lists all minimal unsatisfiable subsets of a given set of formulas. It also
 * provides some static methods for accessing a centrally configured default
 * MUS enumerator.
 * 
 * @author Matthias Thimm
 */
public abstract class PlMusEnumerator extends AbstractMusEnumerator<PlFormula>{

	/** The default MUS enumerator. */
	private static AbstractMusEnumerator<PlFormula> defaultEnumerator = null;
	
	/**
	 * Sets the default MUS enumerator.
	 * @param enumerator some MUS enumerator
	 */
	public static void setDefaultEnumerator(AbstractMusEnumerator<PlFormula> enumerator){
		PlMusEnumerator.defaultEnumerator = enumerator;
	}
	
	/**
	 * Returns "true" if a default MUS enumerator is configured.
	 * @return "true" if a default MUS enumerator is configured.
	 */
	public static boolean hasDefaultEnumerator(){
		return PlMusEnumerator.defaultEnumerator != null;
	}

	/**
	 * Returns the default MUS enumerator.<br><br>
	 * If a default MUS enumerator has been configured this enumerator
	 * is returned by this method. If no default  MUS enumerator is 
	 * configured, a naive enumerator based on the default SAT solver
	 * is returned as a fallback and a message is
	 * printed to stderr pointing out that no default MUS enumerator is configured.
	 * @return the default MUS enumerator.
	 */
	public static AbstractMusEnumerator<PlFormula> getDefaultEnumerator(){
		if(PlMusEnumerator.defaultEnumerator != null)
			return PlMusEnumerator.defaultEnumerator;
		System.err.println("No default MUS enumerator configured, using "
				+ "naive enumerator based on default SAT solver as fallback. "
				+ "It is strongly advised that a default MUS enumerator is manually configured, see "
				+ "'http://tweetyproject.org/doc/mus-enumerators.html' "
				+ "for more information.");
		return new NaiveMusEnumerator<PlFormula>(SatSolver.getDefaultSolver());
	}
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.logics.commons.analysis.AbstractMusEnumerator#minimalInconsistentSubsets(java.util.Collection)
	 */
	@Override
	public abstract Collection<Collection<PlFormula>> minimalInconsistentSubsets(Collection<PlFormula> formulas);
}
