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
package org.tweetyproject.math.opt.problem;

import java.util.*;

import org.tweetyproject.math.equation.*;
import org.tweetyproject.math.term.*;


/**
 * This class models a general constraint satisfaction problem.
 * @author Matthias Thimm
 */
public class ConstraintSatisfactionProblem extends GeneralConstraintSatisfactionProblem{//extends HashSet<Statement>{
	
	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new and empty csp.
	 */
	public ConstraintSatisfactionProblem(){
		super();
	}
	
	/**
	 * Creates a new csp with the given statements
	 * @param statements a collection of statements.
	 */
	public ConstraintSatisfactionProblem(Collection<? extends Statement> statements){
		super(statements);
	}
	
	/**
	 * Normalizes this problem, i.e. every constraint is brought into
	 * an equivalent form "T&gt; 0" or "T &gt;= 0" or "T = 0" or "T != 0". 
	 * @return a csp.
	 */
	public ConstraintSatisfactionProblem toNormalizedForm(){
		ConstraintSatisfactionProblem csp = new ConstraintSatisfactionProblem();
		for(OptProbElement s: this)
			csp.add(((Statement) s).toNormalizedForm());
		return csp;
	}
		
	/**
	 * Checks whether every constraint of this problem is linear.
	 * @return "true" if every constraint of this problem is linear.
	 */
	public boolean isLinear(){
		try{
		for(OptProbElement s: this)
			if(!((Statement) s).getLeftTerm().isLinear() || !((Statement) s).getRightTerm().isLinear())
				return false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Checks whether this problem is integer, i.e. whether all variables
	 * are integer variables.
	 * @return "true" if this problem is integer.
	 */
	public boolean isInteger(){		
		for(Variable v: this.getVariables())
			if(!(v instanceof IntegerVariable))
				return false;
		return true;		
	}
	
	/**
	 * Checks whether this problem uses no minimum function.
	 * @return "true" if this problem uses no minimum function.
	 */
	public boolean isMinimumFree(){
		for(OptProbElement s: this){
			if(!((Statement) s).getLeftTerm().getMinimums().isEmpty())
				 return false;
			if(!((Statement) s).getRightTerm().getMinimums().isEmpty())
				 return false;
		}
		return true;
	}

	/**
	 * Resolves all occurrences of minimums by substituting
	 * a minimum "min{a,b}" by "0.5 a + 0.5 b - abs(a-b)".
	 */
	/*
	 * Sebi: Formel falsch? => eig: 0.5*a+0.5*b-abs(a-b)
	 */
	public void resolveMinimums(){
		// expand all minimums		
		for(OptProbElement s: this)
			((Statement) s).expandAssociativeOperations();
		// resolve minimums in statements
		Set<Statement> newConstraints = new HashSet<Statement>();
		for(OptProbElement s: this){	
			while(!((Statement) s).getMinimums().isEmpty()){
				Minimum m = ((Statement) s).getMinimums().iterator().next();
				Term replacement = new FloatConstant(0.5F);
				replacement = replacement.mult(m.getTerms().get(0));
				replacement = replacement.add((new FloatConstant(0.5F).mult(m.getTerms().get(1))));
				replacement = replacement.minus(new AbsoluteValue(m.getTerms().get(0).minus(m.getTerms().get(1))));
				s = ((Statement) s).replaceTerm(m, replacement);				
			}
			newConstraints.add((Statement) s);
		}	
		this.clear();
		this.addAll(newConstraints);
	}
	
	/**
	 * Resolves all occurrences of maximums by substituting
	 * a maximum "max{a,b}" by "0.5 a + 0.5 b + abs(a-b)".
	 */
	/*
	 * Sebi: Formel falsch? => eig: 0.5*a+0.5*b+abs(a-b)
	 */
	public void resolveMaximums(){
		// expand all maximums		
		for(OptProbElement s: this)
			((Statement) s).expandAssociativeOperations();
		// resolve maximums in statements
		Set<Statement> newConstraints = new HashSet<Statement>();
		for(OptProbElement s: this){	
			while(!((Statement) s).getMaximums().isEmpty()){
				Maximum m = ((Statement) s).getMaximums().iterator().next();
				Term replacement = new FloatConstant(0.5F);
				replacement = replacement.mult(m.getTerms().get(0));
				replacement = replacement.add((new FloatConstant(0.5F).mult(m.getTerms().get(1))));
				replacement = replacement.add(new AbsoluteValue(m.getTerms().get(0).minus(m.getTerms().get(1))));
				s = ((Statement) s).replaceTerm(m, replacement);				
			}
			newConstraints.add((Statement) s);
		}	
		this.clear();
		this.addAll(newConstraints);
	}
	
	/**
	 * Returns all variables of this problem.
	 * @return all variables of this problem.
	 */
	public Set<Variable> getVariables(){
		Set<Variable> variables = new HashSet<Variable>();
		for(OptProbElement s: this){
			variables.addAll(((Statement) s).getLeftTerm().getVariables());
			variables.addAll(((Statement) s).getRightTerm().getVariables());
		}
		return variables;
	}
	
	/**
	 * Returns all minimums appearing in this problem.
	 * @return all minimums appearing in this problem.
	 */
	public Set<Minimum> getMinimums(){
		Set<Minimum> minimums = new HashSet<Minimum>();
		for(OptProbElement s: this){
			minimums.addAll(((Statement) s).getLeftTerm().getMinimums());
			minimums.addAll(((Statement) s).getRightTerm().getMinimums());
		}
		return minimums;
	}
	
	/**
	 * This method collapses all associative operations appearing
	 * in the target function and the constraints, e.g. every
	 * min{min{a,b},c} becomes min{a,b,c}
	 */
	public void collapseAssociativeOperations(){
		for(OptProbElement s: this)
			((Statement) s).collapseAssociativeOperations();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String s = "";
		for(OptProbElement c: this)
			s += c + "\n";
		return s;
	}
	
	/* (non-Javadoc)
	 * @see java.util.HashSet#clone()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ConstraintSatisfactionProblem clone(){
		return new ConstraintSatisfactionProblem((Set<Statement>)super.clone());
	}
		
}
