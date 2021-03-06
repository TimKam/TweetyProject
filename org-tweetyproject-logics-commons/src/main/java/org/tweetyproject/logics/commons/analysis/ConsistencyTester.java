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

import org.tweetyproject.commons.BeliefBase;

/**
 * Classes implementing this interface are capable of testing
 * whether a given belief base is consistent.
 * 
 * @author Matthias Thimm
 * @author Tim Janus
 * @param <T> the type of belief bases
 */
public interface ConsistencyTester<T extends BeliefBase> {
	
	/**
	 * Checks whether the given belief base is consistent.
	 * @param beliefBase a belief base.
	 * @return "true" iff the given belief base is consistent.
	 */
	boolean isConsistent(T beliefBase);
}
