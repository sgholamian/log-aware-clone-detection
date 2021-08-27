//,temp,sample_4614.java,2,11,temp,sample_4615.java,2,11
//,3
public class xxx {
public void alter_partitions_with_environment_context(final String db_name, final String tbl_name, final List<Partition> new_parts, EnvironmentContext environmentContext) throws TException {
startTableFunction("alter_partitions", db_name, tbl_name);
if (LOG.isInfoEnabled()) {
for (Partition tmpPart : new_parts) {


log.info("new partition values");
}
}
}

};