//,temp,ShowDatabasesOperation.java,43,66,temp,ShowTablesOperation.java,80,98
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    List<String> databases = context.getDb().getAllDatabases();
    if (desc.getPattern() != null) {
      LOG.debug("pattern: {}", desc.getPattern());
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      databases = databases.stream().filter(name -> pattern.matcher(name).matches()).collect(Collectors.toList());
    }

    LOG.info("Found {} database(s) matching the SHOW DATABASES statement.", databases.size());

    // write the results in the file
    DataOutputStream outStream = ShowUtils.getOutputStream(new Path(desc.getResFile()), context);
    try {
      ShowDatabasesFormatter formatter = ShowDatabasesFormatter.getFormatter(context.getConf());
      formatter.showDatabases(outStream, databases);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "show databases");
    } finally {
      IOUtils.closeStream(outStream);
    }

    return 0;
  }

};