//,temp,sample_4398.java,2,14,temp,sample_4395.java,2,12
//,3
public class xxx {
private static Operator<?> doSemanticAnalysis(SemanticAnalyzer sem, ASTNode ast, Context ctx) throws SemanticException {
QB qb = new QB(null, null, false);
ASTNode child = ast;
ParseContext subPCtx = sem.getParseContext();
subPCtx.setContext(ctx);
sem.initParseCtx(subPCtx);
sem.doPhase1(child, qb, sem.initPhase1Ctx(), null);
sem.getMetaData(qb);
Operator<?> operator = sem.genPlan(qb);


log.info("sub query completed plan generation");
}

};