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
package org.tweetyproject.logics.rcl.parser;

import java.io.*;

import org.tweetyproject.commons.*;
import org.tweetyproject.logics.fol.parser.*;
import org.tweetyproject.logics.fol.syntax.*;
import org.tweetyproject.logics.rcl.syntax.*;

/**
 * This class implements a parser for relational conditional logic. The BNF for a conditional
 * knowledge base is given by (starting symbol is KB)
 * <br>
 * <br> KB 			::== SORTSDEC PREDDECS (CONDITIONAL "\n")*
 * <br> SORTSDEC    ::== ( SORTNAME "=" "{" (CONSTANTNAME ("," CONSTANTNAME)*)? "}" "\n" )*
 * <br> PREDDECS	::== ( "type" "(" PREDICATENAME "(" (SORTNAME ("," SORTNAME)*)? ")" ")" "\n" )*
 * <br> CONDITIONAL ::== "(" FORMULA ")" "[" PROB "]" | "(" FORMULA "|" FORMULA ")" 
 * <br> FORMULA     ::== ATOM | "(" FORMULA ")" | FORMULA "&amp;&amp;" FORMULA | FORMULA "||" FORMULA | "!" FORMULA | "+" | "-"
 * <br> ATOM		::== PREDICATENAME "(" (TERM ("," TERM)*)? ")"
 * <br> TERM		::== VARIABLENAME | CONSTANTNAME 
 * <br> 
 * <br> where SORTNAME, PREDICATENAME, CONSTANTNAME, and VARIABLENAME are sequences of
 * <br> symbols from {a,...,z,A,...,Z,0,...,9} with a letter at the beginning.
 * 
 *  @author Matthias Thimm
 */
public class RclParser extends Parser<RclBeliefSet,RelationalConditional> {

	/** For parsing FOL fragments. */
	private FolParser folParser;
	
	/**
	  Creates a new RCL Parser
	 */
	public RclParser(){
		folParser = new FolParser();
	}
	
	/* (non-Javadoc)
	 * @see org.tweetyproject.kr.Parser#parseBeliefBase(java.io.Reader)
	 */
	@Override
	public RclBeliefSet parseBeliefBase(Reader reader) throws IOException, ParserException {
		RclBeliefSet beliefSet = new RclBeliefSet();
		String s = "";
		// for keeping track of the section of the file
		// 0 means sorts declaration
		// 1 means functor/predicate declaration
		// 2 means conditional section
		int section = 0; 
		// read from the reader and separate formulas by "\n"
		try{
			for(int c = reader.read(); c != -1; c = reader.read()){
				if(c == 10 || c == 13){
					if(!s.equals("")){
						if(s.trim().startsWith("type")) section = 1;
						else if(section == 1) section = 2;
						
						if(section == 2)
							beliefSet.add(this.parseFormula(new StringReader(s)));
						else if(section == 1)
							this.folParser.parseTypeDeclaration(s,this.folParser.getSignature());
						else this.folParser.parseSortDeclaration(s,this.folParser.getSignature());
					}
					s = "";
				}else{
					s += (char) c;
				}
			}		
			if(!s.equals("")){
				if(s.trim().startsWith("type")) section = 1;
				else if(section == 1) section = 2;
				
				if(section == 2)
					beliefSet.add(this.parseFormula(new StringReader(s)));
				else if(section == 1)
					this.folParser.parseTypeDeclaration(s,this.folParser.getSignature());
				else this.folParser.parseSortDeclaration(s,this.folParser.getSignature());
			}
		}catch(Exception e){
			throw new ParserException(e);
		}
		return beliefSet;
	}

	/* (non-Javadoc)
	 * @see org.tweetyproject.logics.firstorderlogic.parser.FolParser#parseFormula(java.io.Reader)
	 */
	@Override
	public RelationalConditional parseFormula(Reader reader) throws IOException, ParserException {
		// read into string
		String s = "";
		try{
			for(int c = reader.read(); c != -1; c = reader.read())
				s += (char) c;
		}catch(Exception e){
			throw new ParserException(e);
		}
		if(!s.contains("(") || !s.contains(")")) 
			throw new ParserException("Conditionals must be enclosed by parentheses.");
		String condString = s.substring(1,s.lastIndexOf(")"));
		//check for a single "|" (note, that "||" denotes disjunction)
		int idx = 0;		
		while(idx != -1){
			idx = condString.indexOf("|", idx);
			if(condString.charAt(idx+1) != '|')
				break;			
			idx += 2;
		}		
		FolParser parser = new FolParser();
		parser.setSignature(this.folParser.getSignature());
		if(idx == -1){
			RelationalConditional r = new RelationalConditional((FolFormula)parser.parseFormula(condString.substring(0, condString.length())));
			this.folParser.setSignature(parser.getSignature());
			return r;
		}
		// check whether variables have the correct sort wrt. the scope of the whole conditional		
		RelationalConditional cond = new RelationalConditional((FolFormula)parser.parseFormula(condString.substring(idx+1, condString.length())),(FolFormula)parser.parseFormula(condString.substring(0, idx)));
		this.folParser.setSignature(parser.getSignature());
		parser.parseFormula(condString.substring(idx+1, condString.length()) + " && " + condString.substring(0, idx));
		return cond;
	}
	
	/**
	 * Returns the signature of this parser.
	 * @return the signature of this parser.
	 */
	public FolSignature getSignature(){
		return this.folParser.getSignature();
	}
	
}
