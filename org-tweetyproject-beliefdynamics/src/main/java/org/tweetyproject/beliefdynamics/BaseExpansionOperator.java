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
package org.tweetyproject.beliefdynamics;

import java.util.*;

import org.tweetyproject.commons.*;

/**
 * This is the interface for a classic belief base expansion operator, ie. an
 * operator that takes some set of formulas and a single formula and expands
 * the former by the latter. 
 * 
 * @author Matthias Thimm
 *
 * @param <T> The type of formulas that this operator works on.
 */
public interface BaseExpansionOperator<T extends Formula> {

	/**
	 * Expands the given collection of formulas by the given formula.
	 * @param base some collection of formulas.
	 * @param formula a formula
	 * @return the expanded collection.
	 */
	public Collection<T> expand(Collection<T> base, T formula);	
}
