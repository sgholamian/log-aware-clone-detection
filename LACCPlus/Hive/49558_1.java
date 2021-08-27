//,temp,HMSHandler.java,5364,5390,temp,HMSHandler.java,4061,4090
//,3
public class xxx {
  @Override
  public boolean drop_partition_with_environment_context(final String db_name,
                                                         final String tbl_name, final List<String> part_vals, final boolean deleteData,
                                                         final EnvironmentContext envContext)
      throws TException {
    String[] parsedDbName = parseDbName(db_name, conf);
    startPartitionFunction("drop_partition", parsedDbName[CAT_NAME], parsedDbName[DB_NAME],
        tbl_name, part_vals);
    LOG.info("Partition values:" + part_vals);

    boolean ret = false;
    Exception ex = null;
    try {
      ret = drop_partition_common(getMS(), parsedDbName[CAT_NAME], parsedDbName[DB_NAME],
          tbl_name, part_vals, deleteData, envContext);
    } catch (IOException e) {
      ex = e;
      throw new MetaException(e.getMessage());
    } catch (Exception e) {
      ex = e;
      rethrowException(e);
    } finally {
      endFunction("drop_partition", ret, ex, tbl_name);
    }
    return ret;

  }

};