//,temp,sample_4614.java,2,11,temp,sample_4615.java,2,11
//,3
public class xxx {
private void rename_partition(final String db_name, final String tbl_name, final List<String> part_vals, final Partition new_part, final EnvironmentContext envContext) throws TException {
startTableFunction("alter_partition", db_name, tbl_name);
if (LOG.isInfoEnabled()) {
if (part_vals != null && part_vals.size() > 0) {


log.info("old partition values");
}
}
}

};