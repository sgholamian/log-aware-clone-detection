//,temp,CalcitePlanner.java,1608,1618,temp,CalcitePlanner.java,1596,1606
//,2
public class xxx {
  ASTNode reAnalyzeViewAfterCbo(ASTNode newAst) throws SemanticException {
    // analyzeCreateView uses this.ast, but doPhase1 doesn't, so only reset it
    // here.
    newAst = analyzeCreateView(newAst, getQB(), null);
    if (newAst == null) {
      LOG.error("analyzeCreateTable failed to initialize materialized view after CBO;" + " new ast is "
          + getAST().dump());
      throw new SemanticException("analyzeCreateTable failed to initialize materialized view after CBO");
    }
    return newAst;
  }

};