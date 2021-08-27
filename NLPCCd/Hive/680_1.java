//,temp,sample_4613.java,2,9,temp,sample_891.java,2,9
//,3
public class xxx {
private void rename_partition(final String db_name, final String tbl_name, final List<String> part_vals, final Partition new_part, final EnvironmentContext envContext) throws TException {
startTableFunction("alter_partition", db_name, tbl_name);
if (LOG.isInfoEnabled()) {


log.info("new partition values");
}
}

};