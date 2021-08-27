//,temp,sample_4392.java,2,17,temp,sample_1351.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
Context ctx = new Context(queryState.getConf());
ASTNode tree = ParseUtils.parse(command, ctx);
BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);
assert(sem instanceof SemanticAnalyzer);
operatorTree = doSemanticAnalysis((SemanticAnalyzer) sem, tree, ctx);
} catch (IOException e) {
LOG.error(org.apache.hadoop.util.StringUtils.stringifyException(e));
throw new SemanticException(e.getMessage(), e);
} catch (ParseException e) {


log.info("parseexception in generating the operator tree for input command");
}
}

};