/* Generated By:JJTree: Do not edit this line. ASTArithmetic.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package net.sf.tweety.lp.asp.parser;

public
class ASTArithmetic extends SimpleNode {
  public ASTArithmetic(int id) {
    super(id);
  }

  public ASTArithmetic(ASPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ASPParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=653287d6a5efd9f98d4bf18bb743b167 (do not edit this line) */
