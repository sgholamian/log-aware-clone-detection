//,temp,sample_2802.java,2,11,temp,sample_4020.java,2,11
//,3
public class xxx {
public List<Task<? extends Serializable>> handle(Context context) throws SemanticException {
DropFunctionMessage msg = deserializer.getDropFunctionMessage(context.dmd.getPayload());
String actualDbName = context.isDbNameEmpty() ? msg.getDB() : context.dbName;
String qualifiedFunctionName = FunctionUtils.qualifyFunctionName(msg.getFunctionName(), actualDbName);
DropFunctionDesc desc = new DropFunctionDesc( qualifiedFunctionName, false, context.eventOnlyReplicationSpec());
Task<FunctionWork> dropFunctionTask = TaskFactory.get(new FunctionWork(desc), context.hiveConf);


log.info("added drop function task");
}

};