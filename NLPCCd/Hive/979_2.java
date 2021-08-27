//,temp,sample_4397.java,2,13,temp,sample_4394.java,2,11
//,3
public class xxx {
private static Operator<?> doSemanticAnalysis(SemanticAnalyzer sem, ASTNode ast, Context ctx) throws SemanticException {
QB qb = new QB(null, null, false);
ASTNode child = ast;
ParseContext subPCtx = sem.getParseContext();
subPCtx.setContext(ctx);
sem.initParseCtx(subPCtx);


log.info("starting sub query semantic analysis");
}

};