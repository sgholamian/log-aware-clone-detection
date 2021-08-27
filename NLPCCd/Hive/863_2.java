//,temp,sample_3905.java,2,12,temp,sample_4391.java,2,15
//,3
public class xxx {
public static Operator<? extends OperatorDesc> generateOperatorTree(QueryState queryState, String command) throws SemanticException {
Operator<? extends OperatorDesc> operatorTree;
try {
Context ctx = new Context(queryState.getConf());
ASTNode tree = ParseUtils.parse(command, ctx);
BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);
assert(sem instanceof SemanticAnalyzer);
operatorTree = doSemanticAnalysis((SemanticAnalyzer) sem, tree, ctx);
} catch (IOException e) {


log.info("ioexception in generating the operator tree for input command");
}
}

};