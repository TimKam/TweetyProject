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
package org.tweetyproject.arg.lp.semantics.attack;

import org.tweetyproject.arg.lp.syntax.Argument;
import org.tweetyproject.lp.asp.syntax.ASPLiteral;


/**
 * This notion of attack models the rebut relation. 
 * A rebuts B iff there is L in conclusion(A) and \neg L in conclusion(B).
 *  
 * @author Sebastian Homann
 *
 */
public class Rebut implements AttackStrategy {

	/** Singleton instance. */
	private static Rebut instance = new Rebut();
	
	/** Private constructor. */
	private Rebut(){};
	
	/**
	 * Returns the singleton instance of this class.
	 * @return the singleton instance of this class.
	 */
	public static Rebut getInstance(){
		return Rebut.instance;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.tweetyproject.argumentation.parameterisedhierarchy.semantics.attack.NotionOfAttack#attacks(org.tweetyproject.argumentation.parameterisedhierarchy.syntax.Argument, org.tweetyproject.argumentation.parameterisedhierarchy.syntax.Argument)
	 */
	public boolean attacks(Argument a, Argument b) {
		for(ASPLiteral literalA : a.getConclusions()) {
			for(ASPLiteral literalB : b.getConclusions()) {
				if(literalA.complement().equals(literalB)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "rebut";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.tweetyproject.argumentation.parameterisedhierarchy.semantics.attack.AttackStrategy#toAbbreviation()
	 */
	public String toAbbreviation() {
		return "r";
	}
}
