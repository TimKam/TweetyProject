/* Generated By:JJTree: Do not edit this line. ASTAnswerSet.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.sf.tweety.lp.asp.parser;

public
class ASTAnswerSet extends SimpleNode {
  public ASTAnswerSet(int id) {
    super(id);
  }

  public ASTAnswerSet(ASPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ASPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6092e1a6fa06309c437063ed62bd596a (do not edit this line) */
