/* Generated By:JJTree&JavaCC: Do not edit this line. ASPCore2ParserConstants.java */
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
/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ASPCore2ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int COMMENT = 5;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 6;
  /** RegularExpression Id. */
  int NAF = 7;
  /** RegularExpression Id. */
  int ID = 8;
  /** RegularExpression Id. */
  int VARIABLE = 9;
  /** RegularExpression Id. */
  int STRING = 10;
  /** RegularExpression Id. */
  int NUMBER = 11;
  /** RegularExpression Id. */
  int ANONYMOUS_VARIABLE = 12;
  /** RegularExpression Id. */
  int DOT = 13;
  /** RegularExpression Id. */
  int COMMA = 14;
  /** RegularExpression Id. */
  int QUERY_MARK = 15;
  /** RegularExpression Id. */
  int COLON = 16;
  /** RegularExpression Id. */
  int SEMICOLON = 17;
  /** RegularExpression Id. */
  int OR = 18;
  /** RegularExpression Id. */
  int CONS = 19;
  /** RegularExpression Id. */
  int WCONS = 20;
  /** RegularExpression Id. */
  int PLUS = 21;
  /** RegularExpression Id. */
  int MINUS = 22;
  /** RegularExpression Id. */
  int TIMES = 23;
  /** RegularExpression Id. */
  int DIV = 24;
  /** RegularExpression Id. */
  int AT = 25;
  /** RegularExpression Id. */
  int PAREN_OPEN = 26;
  /** RegularExpression Id. */
  int PAREN_CLOSE = 27;
  /** RegularExpression Id. */
  int SQUARE_OPEN = 28;
  /** RegularExpression Id. */
  int SQUARE_CLOSE = 29;
  /** RegularExpression Id. */
  int CURLY_OPEN = 30;
  /** RegularExpression Id. */
  int CURLY_CLOSE = 31;
  /** RegularExpression Id. */
  int EQUAL = 32;
  /** RegularExpression Id. */
  int UNEQUAL = 33;
  /** RegularExpression Id. */
  int LESS = 34;
  /** RegularExpression Id. */
  int GREATER = 35;
  /** RegularExpression Id. */
  int LESS_OR_EQ = 36;
  /** RegularExpression Id. */
  int GREATER_OR_EQ = 37;
  /** RegularExpression Id. */
  int AGGREGATE_COUNT = 38;
  /** RegularExpression Id. */
  int AGGREGATE_MAX = 39;
  /** RegularExpression Id. */
  int AGGREGATE_MIN = 40;
  /** RegularExpression Id. */
  int AGGREGATE_SUM = 41;
  /** RegularExpression Id. */
  int MINIMIZE = 42;
  /** RegularExpression Id. */
  int MAXIMIZE = 43;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<COMMENT>",
    "<MULTI_LINE_COMMENT>",
    "\"not\"",
    "<ID>",
    "<VARIABLE>",
    "<STRING>",
    "<NUMBER>",
    "\"_\"",
    "\".\"",
    "\",\"",
    "\"?\"",
    "\":\"",
    "\";\"",
    "\"|\"",
    "\":-\"",
    "\":~\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"@\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\"{\"",
    "\"}\"",
    "\"=\"",
    "<UNEQUAL>",
    "\"<\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"#count\"",
    "\"#max\"",
    "\"#min\"",
    "\"#sum\"",
    "\"#minimi[zs]e\"",
    "\"#maximi[zs]e\"",
  };

}
