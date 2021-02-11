//,temp,sample_6763.java,2,16,temp,sample_6764.java,2,16
//,2
public class xxx {
public void updateParameters(Map<String, String> parameters) throws IOException {
Preconditions.checkNotNull(table, "Not initialized");
TableDescription desc = getTableDescription(true);
ProvisionedThroughputDescription current = desc.getProvisionedThroughput();
long currentRead = current.getReadCapacityUnits();
long newRead = getLongParam(parameters, S3GUARD_DDB_TABLE_CAPACITY_READ_KEY, currentRead);
long currentWrite = current.getWriteCapacityUnits();
long newWrite = getLongParam(parameters, S3GUARD_DDB_TABLE_CAPACITY_WRITE_KEY, currentWrite);
ProvisionedThroughput throughput = new ProvisionedThroughput() .withReadCapacityUnits(newRead) .withWriteCapacityUnits(newWrite);
if (newRead != currentRead || newWrite != currentWrite) {


log.info("current table capacity is read write");
}
}

};