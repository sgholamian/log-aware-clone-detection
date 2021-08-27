//,temp,sample_2833.java,2,11,temp,sample_2834.java,2,16
//,3
public class xxx {
public void analyzeInternal(ASTNode ast) throws SemanticException {
LOG.debug(ast.getName() + ":" + ast.getToken().getText() + "=" + ast.getText());
switch (ast.getToken().getType()) {
case TOK_REPL_DUMP: {
initReplDump(ast);
analyzeReplDump(ast);
break;
}
case TOK_REPL_LOAD: {


log.info("replicationsemanticanalyzer analyzeinternal load");
}
}
}

};