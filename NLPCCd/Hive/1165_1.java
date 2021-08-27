//,temp,sample_5170.java,2,8,temp,sample_5171.java,2,10
//,3
public class xxx {
private void fireInsertEvent(Table tbl, Map<String, String> partitionSpec, boolean replace, List<Path> newFiles) throws HiveException {
if (conf.getBoolVar(ConfVars.FIRE_EVENTS_FOR_DML)) {


log.info("firing dml insert event");
}
}

};