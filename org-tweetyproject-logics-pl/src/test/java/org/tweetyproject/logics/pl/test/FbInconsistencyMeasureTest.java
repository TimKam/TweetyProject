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
package org.tweetyproject.logics.pl.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.analysis.FbInconsistencyMeasure;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 * Some examples from
 * [Besnard. Forgetting-based Inconsistency Measure. SUM 2016]
 * 
 * @author Matthias Thimm
 *
 */
public class FbInconsistencyMeasureTest {
	
	FbInconsistencyMeasure inc = new FbInconsistencyMeasure();
	
	@Before
	public void setUp() {		
		SatSolver.setDefaultSolver(new Sat4jSolver());
	}
	
	@Test
	public void example1() throws ParserException, IOException{
		PlBeliefSet bs = new PlBeliefSet();
		PlParser parser = new PlParser();
		bs.add((PlFormula) parser.parseFormula("a || a"));
		bs.add((PlFormula) parser.parseFormula("!a || !a"));
		
		assertEquals(inc.inconsistencyMeasure(bs), Double.valueOf(1d));		
	
	}
	
	@Test
	public void example2() throws ParserException, IOException{
		PlBeliefSet bs = new PlBeliefSet();
		PlParser parser = new PlParser();
		bs.add((PlFormula) parser.parseFormula("a && a"));
		bs.add((PlFormula) parser.parseFormula("!a && !a"));
		
		assertEquals(inc.inconsistencyMeasure(bs), Double.valueOf(2d));		
	}
	
	@Test
	public void example3() throws ParserException, IOException{
		PlBeliefSet bs = new PlBeliefSet();
		PlParser parser = new PlParser();
		bs.add((PlFormula) parser.parseFormula("(a && !a) || (b && !b)"));
		
		assertEquals(inc.inconsistencyMeasure(bs), Double.valueOf(1d));		
	}
	
	@Test
	public void example4() throws ParserException, IOException{
		PlBeliefSet bs = new PlBeliefSet();
		PlParser parser = new PlParser();
		bs.add((PlFormula) parser.parseFormula("!a && !a && !a"));
		bs.add((PlFormula) parser.parseFormula("a"));
		
		assertEquals(inc.inconsistencyMeasure(bs), Double.valueOf(1d));
		
		bs = new PlBeliefSet();		
		bs.add((PlFormula) parser.parseFormula("!a && !a && !a"));
		bs.add((PlFormula) parser.parseFormula("a && a && a"));
		
		assertEquals(inc.inconsistencyMeasure(bs), Double.valueOf(3d));
	}
}
