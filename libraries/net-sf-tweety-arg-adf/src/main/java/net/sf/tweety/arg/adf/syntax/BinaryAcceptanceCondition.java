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
package net.sf.tweety.arg.adf.syntax;

import java.util.function.Consumer;
import java.util.stream.Stream;

import net.sf.tweety.arg.adf.transform.Transform;

/**
 * @author Mathias Hofer
 *
 */
public abstract class BinaryAcceptanceCondition extends AcceptanceCondition {

	private AcceptanceCondition left;

	private AcceptanceCondition right;

	/**
	 * @param left
	 * @param right
	 */
	public BinaryAcceptanceCondition(AcceptanceCondition left, AcceptanceCondition right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Stream<Argument> arguments() {
		return Stream.concat(left.arguments(), right.arguments());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.tweety.arg.adf.syntax.AcceptanceCondition#transform(net.sf.tweety.
	 * arg.adf.syntax.Transform, java.util.function.Consumer)
	 */
	@Override
	protected <C, R> R transform(Transform<C, R> transform, Consumer<C> consumer, int polarity) {
		R leftTransformed = left.transform(transform, consumer, leftPolarity(polarity));
		R rightTransformed = right.transform(transform, consumer, rightPolarity(polarity));
		return transform(transform, consumer, leftTransformed, rightTransformed, polarity);
	}

	protected abstract int leftPolarity(int polarity);

	protected abstract int rightPolarity(int polarity);

	protected abstract <C, R> R transform(Transform<C, R> transform, Consumer<C> consumer, R left, R right,
			int polarity);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder(getName()).append("(").append(left).append(",").append(right).append(")").toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BinaryAcceptanceCondition other = (BinaryAcceptanceCondition) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
}