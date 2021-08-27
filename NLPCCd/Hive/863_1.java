//,temp,sample_3905.java,2,12,temp,sample_4391.java,2,15
//,3
public class xxx {
private void rewriteForIndexes(ExprNodeDesc predicate, List<Index> indexes, ParseContext pctx, Task<MapredWork> task, HiveIndexQueryContext queryContext) throws SemanticException {
HiveIndexHandler indexHandler;
Index index = indexes.get(0);
try {
indexHandler = HiveUtils.getIndexHandler(pctx.getConf(), index.getIndexHandlerClass());
} catch (HiveException e) {


log.info("exception while loading indexhandler");
}
}

};