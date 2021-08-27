//,temp,Hive.java,5541,5549,temp,Hive.java,4147,4154
//,3
public class xxx {
  public void validatePartitionNameCharacters(List<String> partVals) throws HiveException {
    try {
      getMSC().validatePartitionNameCharacters(partVals);
    } catch (Exception e) {
      LOG.error("Failed validatePartitionNameCharacters", e);
      throw new HiveException(e);
    }
  }

};