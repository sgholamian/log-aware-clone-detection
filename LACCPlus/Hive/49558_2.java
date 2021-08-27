//,temp,HMSHandler.java,5364,5390,temp,HMSHandler.java,4061,4090
//,3
public class xxx {
  @Override
  public Partition append_partition_with_environment_context(final String dbName,
                                                             final String tableName, final List<String> part_vals, final EnvironmentContext envContext)
      throws InvalidObjectException, AlreadyExistsException, MetaException {
    if (part_vals == null || part_vals.isEmpty()) {
      throw new MetaException("The partition values must not be null or empty.");
    }
    String[] parsedDbName = parseDbName(dbName, conf);
    startPartitionFunction("append_partition", parsedDbName[CAT_NAME], parsedDbName[DB_NAME], tableName, part_vals);
    if (LOG.isDebugEnabled()) {
      for (String part : part_vals) {
        LOG.debug(part);
      }
    }

    Partition ret = null;
    Exception ex = null;
    try {
      ret = append_partition_common(getMS(), parsedDbName[CAT_NAME], parsedDbName[DB_NAME], tableName, part_vals, envContext);
    } catch (MetaException | InvalidObjectException | AlreadyExistsException e) {
      ex = e;
      throw e;
    } catch (Exception e) {
      ex = e;
      throw newMetaException(e);
    } finally {
      endFunction("append_partition", ret != null, ex, tableName);
    }
    return ret;
  }

};