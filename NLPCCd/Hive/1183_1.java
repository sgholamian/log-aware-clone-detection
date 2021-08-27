//,temp,sample_4242.java,2,11,temp,sample_4344.java,2,11
//,3
public class xxx {
public void run(HookContext hookContext) throws Exception {
assert (hookContext.getHookType() == HookContext.HookType.POST_EXEC_HOOK);
HiveConf conf = hookContext.getConf();
if (!"tez".equals(HiveConf.getVar(conf, HiveConf.ConfVars.HIVE_EXECUTION_ENGINE))) {
return;
}


log.info("executing post execution hook to print tez summary");
}

};