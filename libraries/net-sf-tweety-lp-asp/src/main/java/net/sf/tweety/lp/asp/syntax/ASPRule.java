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
 *  Copyright 2018 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.lp.asp.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.tweety.commons.util.rules.Rule;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolSignature;

/**
 * This class models a rule in ASP. A rule consists of a head and a body. The
 * head contains 0+ classical atoms and the body contains 0+ literals. Rules
 * with non-empty heads and empty bodies are called facts, rules with empty
 * heads and non-empty bodies are called constraints.
 *
 * @author Anna Gessler
 * @author Tim Janus
 * @author Thomas Vengels
 */
public class ASPRule extends ASPElement implements Rule<ASPHead, ASPBodyElement>, Comparable<ASPRule> {

	/**
	 * The head (conclusion) of a rule. Consists of a disjunction of atoms.
	 */
	private ASPHead head;

	/**
	 * The body (premise) of a rule. Consists of literals.
	 */
	private List<ASPBodyElement> body;

	/**
	 * Additional attributes that can be used for weak constraints (rules with empty
	 * heads).
	 */
	private Term<?> weight;
	private Term<?> level;
	private List<Term<?>> constraint_terms;

	// -------------------------------------------------------------------------
	// CONSTRUCTORS
	// -------------------------------------------------------------------------

	/**
	 * Empty constructor
	 */
	public ASPRule() {
		this.head = new ClassicalHead();
		this.body = new LinkedList<ASPBodyElement>();
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a fact with the given ASPHead.
	 * 
	 * @param head an ASPHead
	 */
	public ASPRule(ASPHead head) {
		this.head = head;
		this.body = new LinkedList<ASPBodyElement>();
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a fact with the given literal.
	 * 
	 * @param literal a literal
	 */
	public ASPRule(ASPLiteral literal) {
		this();
		this.head = new ClassicalHead(literal);
	}

	/**
	 * Creates a rule with the given head and body.
	 * 
	 * @param head an ASPHead
	 * @param body a list of ASPBodyElement
	 */
	public ASPRule(ASPHead head, List<ASPBodyElement> body) {
		this.head = head;
		this.body = body;
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a rule with the given head and a single-element body.
	 * 
	 * @param head an ASPLiteral
	 * @param b    a body element
	 */
	public ASPRule(ASPLiteral head, ASPBodyElement b) {
		this(head);
		this.body.add(b);
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a rule with the given head and a single-element body.
	 * 
	 * @param head an ASPHead
	 * @param b    a body element
	 */
	public ASPRule(ASPHead head, ASPBodyElement b) {
		this(head);
		this.body.add(b);
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a rule with the given head and body.
	 * 
	 * @param head an ASPLiteral
	 * @param body a list of ASPBodyElement
	 */
	public ASPRule(ASPLiteral head, List<ASPBodyElement> body) {
		this.head = new ClassicalHead(head);
		this.body = body;
		this.weight = null;
		this.level = null;
		this.constraint_terms = new LinkedList<Term<?>>();
	}

	/**
	 * Creates a constraint with the given body.
	 * 
	 * @param body a list of ASPBodyElement
	 */
	public ASPRule(List<ASPBodyElement> body) {
		this();
		this.body = body;
	}

	/**
	 * Creates a constraint with the given weight and terms.
	 * 
	 * @param nafliterals the naf literals
	 * @param weight      some weight
	 * @param terms       a list of terms
	 */
	public ASPRule(List<ASPBodyElement> nafliterals, Term<?> weight, List<Term<?>> terms) {
		this();
		this.body = nafliterals;
		this.weight = weight;
		this.constraint_terms = terms;
	}

	/**
	 * Creates a constraint with the given weight, level and terms.
	 * 
	 * @param body   a list of ASPBodyElement
	 * @param weight a term
	 * @param level  a term
	 * @param terms  a list of terms
	 */
	public ASPRule(List<ASPBodyElement> body, Term<?> weight, Term<?> level, List<Term<?>> terms) {
		this.head = new ClassicalHead();
		this.body = body;
		this.weight = weight;
		this.level = level;
		this.constraint_terms = terms;
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param other another ASPRule
	 */
	public ASPRule(ASPRule other) {
		this(other.body, other.weight, other.level, other.constraint_terms);
		this.head = other.head;
	}

	// -------------------------------------------------------------------------
	// GETTERS AND SETTERS
	// some of those methods are redundant (e.g. getPremise is equivalent
	// to getBody), they exist for convenience since some users might be more
	// familiar with the names body/head vs premise/conclusion.
	// -------------------------------------------------------------------------

	@Override
	public void addPremise(ASPBodyElement premise) {
		this.body.add(premise);
	}

	@Override
	public List<ASPBodyElement> getPremise() {
		return body;
	}

	@Override
	public void addPremises(Collection<? extends ASPBodyElement> premises) {
		this.body.addAll(premises);
	}

	/**
	 * Add the given body element to this rule.
	 * 
	 * @param premise
	 */
	public void addBody(ASPBodyElement premise) {
		addPremise(premise);
	}

	/**
	 * @return the body of this rule
	 */
	public List<ASPBodyElement> getBody() {
		return getPremise();
	}

	/**
	 * Add the given body elements to this rule.
	 * 
	 * @param premises
	 */
	public void addBodyElements(Collection<? extends ASPBodyElement> premises) {
		addPremises(premises);
	}

	/**
	 * Set this rule's body to the given ASPBodyElements.
	 * 
	 * @param aspBodyElements
	 */
	public void setBody(ASPBodyElement... aspBodyElements) {
		List<ASPBodyElement> bes = new ArrayList<ASPBodyElement>();
		for (ASPBodyElement a : aspBodyElements)
			bes.add(a);
		this.body = bes;
	}

	@Override
	public void setConclusion(ASPHead conclusion) {
		this.head = conclusion;
	}

	/**
	 * Set the conclusion of this rule.
	 * 
	 * @param head
	 */
	public void setConclusion(ASPLiteral head) {
		this.head = new ClassicalHead(head);
	}

	@Override
	public ASPHead getConclusion() {
		return head;
	}

	/**
	 * Add the given literal to the head of the rule.
	 * 
	 * @param literal ASPLiteral
	 */
	public void addToHead(ASPLiteral literal) {
		if (this.head instanceof ClassicalHead)
			((ClassicalHead) this.head).add(literal);
		else
			throw new UnsupportedOperationException(
					"This function is only supported for classical heads (disjunctions of literals)");
	}

	/**
	 * @return head of this rule
	 */
	public ASPHead getHead() {
		return getConclusion();
	}

	/**
	 * Set the head of this rule.
	 * 
	 * @param head
	 */
	public void setHead(ASPHead head) {
		setConclusion(head);
	}

	/**
	 * Set the head of this rule.
	 * 
	 * @param head
	 */
	public void setHead(ASPLiteral head) {
		setConclusion(head);
	}

	/**
	 * @return the weight of this constraint.
	 */
	public Term<?> getWeight() {
		return weight;
	}

	/**
	 * Set the weight attribute of this constraint.
	 * 
	 * @param weight a term
	 */
	public void setWeight(Term<?> weight) {
		this.weight = weight;
	}

	/**
	 * @return the level attribute of this constraint.
	 */
	public Term<?> getLevel() {
		return level;
	}

	/**
	 * Set the level attribute of this constraint.
	 * 
	 * @param level a term
	 */
	public void setLevel(Term<?> level) {
		this.level = level;
	}

	/**
	 * @return the constraint terms of this rule.
	 */
	public List<Term<?>> getConstraintTerms() {
		return constraint_terms;
	}

	// -------------------------------------------------------------------------
	// INTERFACE METHODS AND UTILS
	// -------------------------------------------------------------------------
	
	@Override
	public boolean isFact() {
		return (body.isEmpty() && !head.isEmpty());
	}

	@Override
	public boolean isConstraint() {
		return (head.isEmpty() && !body.isEmpty());
	}

	/**
	 * @return true if the rule's head and body are both empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.head.isEmpty() && this.body.isEmpty();
	}

	/**
	 * This methods tests a rule for safety. Safety is defined as follows in the
	 * ASP-Core-2 standard: A rule, weak constraint or query is safe, if the set V
	 * of global variables
	 * 
	 * @return true if the rule is safe, false otherwise
	 */
	public Boolean isSafe() {
		// Get all variables in the rule
		// Set<Variable> vars = this.getTerms(Variable.class);
		// TODO
		throw new UnsupportedOperationException("WIP");
	}

	public boolean isGround() {
		if (!head.isGround())
			return false;
		for (ASPBodyElement a : body)
			if (!a.isGround())
				return false;
		return true;
	}

	@Override
	public FolSignature getSignature() {
		FolSignature sig = new FolSignature();
		sig.add(head.getSignature());
		for (ASPBodyElement a : body)
			sig.add(a.getSignature());
		return sig;
	}

	@Override
	public boolean isLiteral() {
		return false;
	}

	@Override
	public Set<Term<?>> getTerms() {
		Set<Term<?>> terms = new HashSet<Term<?>>();
		terms.addAll(head.getTerms());
		for (ASPBodyElement be : body)
			terms.addAll(be.getTerms());
		if (level != null)
			terms.addAll(level.getTerms());
		if (weight != null)
			terms.addAll(weight.getTerms());
		terms.addAll(constraint_terms);
		return terms;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		Set<C> terms = new HashSet<C>();
		terms.addAll(head.getTerms(cls));
		for (ASPBodyElement be : body)
			terms.addAll(be.getTerms(cls));
		if (level != null)
			terms.addAll(level.getTerms(cls));
		if (weight != null)
			terms.addAll(weight.getTerms(cls));
		for (Term<?> t : constraint_terms)
			terms.addAll(t.getTerms(cls));
		return terms;
	}

	@Override
	public Set<Predicate> getPredicates() {
		Set<Predicate> predicates = new HashSet<Predicate>();
		predicates.addAll(head.getPredicates());
		for (ASPBodyElement be : body)
			predicates.addAll(be.getPredicates());
		return predicates;
	}

	@Override
	public Set<ASPAtom> getAtoms() {
		Set<ASPAtom> atoms = new HashSet<ASPAtom>();
		atoms.addAll(head.getAtoms());
		for (ASPBodyElement be : body)
			atoms.addAll(be.getAtoms());
		return atoms;
	}

	public Collection<? extends ASPLiteral> getLiterals() {
		SortedSet<ASPLiteral> literals = new TreeSet<ASPLiteral>();
		literals.addAll(head.getLiterals());
		for (ASPBodyElement pe : body) {
			literals.addAll(pe.getLiterals());
		}
		return literals;
	}

	public ASPRule substitute(Term<?> v, Term<?> t) {
		ASPRule reval = new ASPRule();
		reval.head = (ASPHead) head.substitute(v, t);
		for (ASPBodyElement bodyElement : body)
			reval.body.add(bodyElement.substitute(v, t));
		return reval;
	}

	public ASPRule exchange(Term<?> v, Term<?> t) {
		ASPRule reval = new ASPRule();
		reval.head = (ASPHead) head.exchange(v, t);
		for (ASPBodyElement bodyElement : body)
			reval.body.add((ASPBodyElement) bodyElement.exchange(v, t));
		return reval;
	}

	@Override
	public int compareTo(ASPRule arg0) {
		int comp = 0;

		// facts first:
		if (getPremise().size() == 0 && arg0.getPremise().size() != 0) {
			return -1;
		} else if (getPremise().size() != 0 && arg0.getPremise().size() == 0) {
			return 1;
		}

		// then order alphabetically starting by the head.
		comp = getConclusion().toString().compareTo(arg0.getConclusion().toString());
		if (comp != 0)
			return comp;

		// TODO: This implementation is not compatible with equals, because
		// the comparison depends on the order in which the elements
		// have been added to the premise list. Ex.:
		// compareTo(:- a,b., :- b,a.) = -1
		// if the head is the same use the body.
		for (int i = 0; i < body.size() && i < arg0.body.size(); ++i) {
			comp = body.get(i).toString().compareTo(arg0.body.get(i).toString());
			if (comp != 0)
				return comp;
		}

		return comp;
	}

	@Override
	public ASPRule clone() {
		return new ASPRule(this);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Rule))
			return false;
		ASPRule or = (ASPRule) other;

		boolean reval = this.head.equals(or.head) && this.body.equals(or.body);
		return reval;
	}

	@Override
	public int hashCode() {
		return head.hashCode() + body.hashCode();
	}

	@Override
	public String toString() {
		String ret = "";

		if (!head.isEmpty()) {
			if (head instanceof ClassicalHead) {
				for (int i = 0; i < ((ClassicalHead) head).size() - 1; i++)
					ret += ((ClassicalHead) head).get(i).toString() + ",";
				ret += ((ClassicalHead) head).get(((ClassicalHead) head).size() - 1).toString();
			} else
				ret += head.toString();
		}
		if (!body.isEmpty()) {
			ret += " :- " + body.get(0);
			for (int i = 1; i < body.size(); ++i) {
				ret += ", " + body.get(i);
			}
		}
		ret += ".";

		if (weight != null) {
			ret += " [" + weight.toString();
			if (level != null)
				ret += "@" + level.toString();
			if (!this.constraint_terms.isEmpty()) {
				ret += ",";
				for (int i = 1; i < constraint_terms.size() - 1; i++)
					ret += constraint_terms.get(i) + ",";
				ret += constraint_terms.get(constraint_terms.size() - 1).toString();
			}
			ret += "]";
		}

		return ret;
	}

	@Override
	public String printToClingo() {
		String ret = "";

		if (!head.isEmpty()) {
			if (head instanceof ClassicalHead) {
				for (int i = 0; i < ((ClassicalHead) head).size() - 1; i++)
					ret += ((ClassicalHead) head).get(i).printToClingo() + ",";
				ret += ((ClassicalHead) head).get(((ClassicalHead) head).size() - 1).printToClingo();
			} else
				ret += head.printToClingo();
		}
		if (!body.isEmpty()) {
			ret += " :- " + body.get(0);
			for (int i = 1; i < body.size(); ++i) {
				ret += ", " + body.get(i);
			}
		}
		ret += ".";

		if (weight != null) {
			ret += " [" + weight.toString();
			if (level != null)
				ret += "@" + level.toString();
			if (!this.constraint_terms.isEmpty()) {
				ret += ",";
				for (int i = 1; i < constraint_terms.size() - 1; i++)
					ret += constraint_terms.get(i) + ",";
				ret += constraint_terms.get(constraint_terms.size() - 1).toString();
			}
			ret += "]";
		}

		return ret;
	}

}
