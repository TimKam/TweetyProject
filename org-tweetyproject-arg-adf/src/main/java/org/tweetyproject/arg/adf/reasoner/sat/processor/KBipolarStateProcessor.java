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
package org.tweetyproject.arg.adf.reasoner.sat.processor;

import org.tweetyproject.arg.adf.reasoner.sat.encodings.BipolarSatEncoding;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.KBipolarSatEncoding;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.PropositionalMapping;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.SatEncoding;
import org.tweetyproject.arg.adf.sat.SatSolverState;
import org.tweetyproject.arg.adf.syntax.adf.AbstractDialecticalFramework;

/**
 * @author Mathias Hofer
 *
 */
public final class KBipolarStateProcessor implements StateProcessor {

	private final SatEncoding bipolar = new BipolarSatEncoding();
	
	private final SatEncoding kBipolar = new KBipolarSatEncoding();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tweetyproject.arg.adf.reasoner.processor.StateProcessor#process(java.lang
	 * .Object, org.tweetyproject.arg.adf.syntax.AbstractDialecticalFramework)
	 */
	@Override
	public void process(SatSolverState state, PropositionalMapping encodingContext, AbstractDialecticalFramework adf) {
		bipolar.encode(state::add, encodingContext, adf);
		if (!adf.bipolar()) {
			kBipolar.encode(state::add, encodingContext, adf);
		}
	}

}
