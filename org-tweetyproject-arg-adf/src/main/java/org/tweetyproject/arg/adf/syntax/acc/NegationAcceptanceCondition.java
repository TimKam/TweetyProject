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
 *  Copyright 2019 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package org.tweetyproject.arg.adf.syntax.acc;

import java.util.Set;

public final class NegationAcceptanceCondition extends AbstractAcceptanceCondition {

	private final AcceptanceCondition child;
	
	/**
	 * 
	 * @param child the child of the negation
	 */
	public NegationAcceptanceCondition(AcceptanceCondition child) {
		super(Set.of(child));
		this.child = child;
	}
	
	/**
	 * @return the child
	 */
	public AcceptanceCondition getChild() {
		return child;
	}

	/* (non-Javadoc)
	 * @see org.tweetyproject.arg.adf.syntax.acc.AcceptanceCondition#accept(org.tweetyproject.arg.adf.syntax.acc.Visitor, java.lang.Object)
	 */
	@Override
	public <U, D> U accept(Visitor<U, D> visitor, D topDownData) {
		return visitor.visit(this, topDownData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tweetyproject.arg.adf.syntax.acc.AcceptanceCondition#getName()
	 */
	@Override
	public String getName() {
		return "neg";
	}

}
