//,temp,sample_2269.java,2,13,temp,sample_2270.java,2,13
//,2
public class xxx {
public static void processFileSink(GenTezProcContext context, FileSinkOperator fileSink) throws SemanticException {
ParseContext parseContext = context.parseContext;
boolean isInsertTable = GenMapRedUtils.isInsertInto(parseContext, fileSink);
HiveConf hconf = parseContext.getConf();
boolean chDir = GenMapRedUtils.isMergeRequired(context.moveTask, hconf, fileSink, context.currentTask, isInsertTable);
Path finalName = GenMapRedUtils.createMoveTask(context.currentTask, chDir, fileSink, parseContext, context.moveTask, hconf, context.dependencyTask);
if (chDir) {


log.info("using combinehiveinputformat for the merge job");
}
}

};