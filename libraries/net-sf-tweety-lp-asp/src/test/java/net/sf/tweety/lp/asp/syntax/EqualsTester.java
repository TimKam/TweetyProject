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
package net.sf.tweety.lp.asp.syntax;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import net.sf.tweety.logics.commons.syntax.Constant;
import net.sf.tweety.logics.commons.syntax.FunctionalTerm;
import net.sf.tweety.logics.commons.syntax.Functor;
import net.sf.tweety.logics.commons.syntax.NumberTerm;
import net.sf.tweety.logics.commons.syntax.Variable;

import org.junit.Test;

public class EqualsTester {
	@Test
	public void testELPAtom() {
		ASPAtom a1 = new ASPAtom("test", new NumberTerm(1));
		ASPAtom a2 = new ASPAtom("test", new NumberTerm(1), new NumberTerm(2));
		
		assertEquals(false, a1.equals(a2));
		assertEquals(true, a1.equals(new ASPAtom("test", new NumberTerm(1))));
		assertEquals(false, a1.equals(new ASPAtom("test", new NumberTerm(2))));
	}
	
	@Test
	public void testCloneEqualAtom() {
		ASPAtom atom = new ASPAtom("blub", new Variable("X"), new Constant("y"), 
				new FunctionalTerm(new Functor("test"), new NumberTerm(1), new Variable("X"), new Constant("x")));
		assertEquals(true, atom.equals(atom.clone())); 
	}
	
	@Test
	public void testRule() {
		ASPRule r1 = new ASPRule();
		r1.setConclusion(new ASPAtom("test"));
		
		ASPRule r2 = new ASPRule(new ASPAtom("test"), new ASPAtom("not_released"));
		assertEquals(false, r1.equals(r2));
	}
	
	@Test
	public void testProgram() {
		Program p1 = new Program();
		Program p2 = new Program();
		
		assertEquals(true, p1.equals(p2));
		p1.add(new ASPRule(new ASPAtom("test")));
		assertEquals(false, p1.equals(p2));
	}
	
	@Test
	public void testAddAll() {
		ASPRule r1 = new ASPRule(new ASPAtom("a1"));
		ASPRule r2 = new ASPRule(new ASPAtom("a2"));
		ASPRule r3 = new ASPRule(new ASPAtom("a3"));
		
		List<ASPRule> rules = new LinkedList<ASPRule>();
		rules.add(r1);
		rules.add(r2);
		rules.add(r3);
		Program p2 = new Program();
		p2.addAll(rules);
		
		assertEquals(true, p2.contains(r3));
	}
}
