/* Generated By:JJTree&JavaCC: Do not edit this line. ASPCore2ParserTokenManager.java */
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
package net.sf.tweety.lp.asp.parser;

/* Generated By:JJTree: Do not edit this line. ASTID.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public class ASTID extends SimpleNode {
	protected String name;

	public ASTID(int id) {
		super(id);
	}

	public ASTID(ASPCore2Parser p, int id) {
		super(p, id);
	}

	/** Accept the visitor. **/
	public Object jjtAccept(ASPCore2ParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public void name(String image) {
		this.name = image;

	}
}
/*
 * JavaCC - OriginalChecksum=9732247f0eb8422c5938e5bf22eb1789 (do not edit this
 * line)
 */
