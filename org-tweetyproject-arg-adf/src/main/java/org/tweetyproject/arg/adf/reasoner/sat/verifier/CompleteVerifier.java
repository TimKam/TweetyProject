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
package org.tweetyproject.arg.adf.reasoner.sat.verifier;

import java.util.Iterator;

import org.tweetyproject.arg.adf.reasoner.sat.encodings.ConflictFreeInterpretationSatEncoding;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.FixPartialSatEncoding;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.LargerInterpretationSatEncoding;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.PropositionalMapping;
import org.tweetyproject.arg.adf.reasoner.sat.encodings.SatEncoding;
import org.tweetyproject.arg.adf.sat.SatSolverState;
import org.tweetyproject.arg.adf.semantics.interpretation.Interpretation;
import org.tweetyproject.arg.adf.syntax.Argument;
import org.tweetyproject.arg.adf.syntax.adf.AbstractDialecticalFramework;
import org.tweetyproject.arg.adf.syntax.pl.Atom;
import org.tweetyproject.arg.adf.transform.TseitinTransformer;

/**
 * @author Mathias Hofer
 *
 */
public class CompleteVerifier implements Verifier {

	private final SatEncoding conflictFree = new ConflictFreeInterpretationSatEncoding();
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.arg.adf.reasoner.sat.verifier.Verifier#prepareState(org.tweetyproject.arg.adf.sat.SatSolverState, org.tweetyproject.arg.adf.reasoner.sat.encodings.PropositionalMapping, org.tweetyproject.arg.adf.syntax.adf.AbstractDialecticalFramework)
	 */
	@Override
	public void prepareState(SatSolverState state, PropositionalMapping mapping, AbstractDialecticalFramework adf) {
		conflictFree.encode(state::add, mapping, adf);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tweetyproject.arg.adf.reasoner.verifier.Verifier#verify(java.lang.Object,
	 * org.tweetyproject.arg.adf.semantics.Interpretation,
	 * org.tweetyproject.arg.adf.syntax.AbstractDialecticalFramework)
	 */
	@Override
	public boolean verify(SatSolverState state, PropositionalMapping mapping, Interpretation candidate,
			AbstractDialecticalFramework adf) {
		boolean complete = true;
		Iterator<Argument> undecided = candidate.undecided().iterator();
		new FixPartialSatEncoding(candidate).encode(state::add, mapping, adf);
		new LargerInterpretationSatEncoding(candidate).encode(state::add, mapping, adf);
		while (undecided.hasNext() && complete) {
			Argument s = undecided.next();
			TseitinTransformer transformer = TseitinTransformer.ofPositivePolarity(r -> mapping.getLink(r, s), false);
			Atom accName = transformer.collect(adf.getAcceptanceCondition(s), state::add);
			
			// check not-taut
			state.assume(accName, false);
			boolean notTaut = state.satisfiable();

			// check not-unsat
			state.assume(accName, true);
			boolean notUnsat = state.satisfiable();

			complete = notTaut && notUnsat;
		}
		return complete;
	}
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.arg.adf.reasoner.sat.verifier.Verifier#postVerification(org.tweetyproject.arg.adf.sat.SatSolverState, org.tweetyproject.arg.adf.reasoner.sat.encodings.PropositionalMapping, org.tweetyproject.arg.adf.semantics.interpretation.Interpretation, org.tweetyproject.arg.adf.syntax.adf.AbstractDialecticalFramework, boolean)
	 */
	@Override
	public boolean postVerification(SatSolverState state, PropositionalMapping mapping, Interpretation candidate,
			AbstractDialecticalFramework adf, boolean verificationResult) {
		return true;
	}

}
