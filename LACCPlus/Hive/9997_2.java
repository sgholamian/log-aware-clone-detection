//,temp,CalcitePlanner.java,1608,1618,temp,CalcitePlanner.java,1596,1606
//,2
public class xxx {
  ASTNode reAnalyzeCTASAfterCbo(ASTNode newAst) throws SemanticException {
    // analyzeCreateTable uses this.ast, but doPhase1 doesn't, so only reset it
    // here.
    newAst = analyzeCreateTable(newAst, getQB(), null);
    if (newAst == null) {
      LOG.error("analyzeCreateTable failed to initialize CTAS after CBO;" + " new ast is "
          + getAST().dump());
      throw new SemanticException("analyzeCreateTable failed to initialize CTAS after CBO");
    }
    return newAst;
  }

};