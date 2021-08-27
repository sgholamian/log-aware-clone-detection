//,temp,ShowTablesOperation.java,62,78,temp,ShowDataConnectorsOperation.java,43,66
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    List<String> connectors = context.getDb().getAllDataConnectorNames();
    if (desc.getPattern() != null) {
      LOG.debug("pattern: {}", desc.getPattern());
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      connectors = connectors.stream().filter(name -> pattern.matcher(name).matches()).collect(Collectors.toList());
    }

    LOG.info("Found {} connector(s) matching the SHOW CONNECTORS statement.", connectors.size());

    // write the results in the file
    DataOutputStream outStream = ShowUtils.getOutputStream(new Path(desc.getResFile()), context);
    try {
      ShowDataConnectorsFormatter formatter = ShowDataConnectorsFormatter.getFormatter(context.getConf());
      formatter.showDataConnectors(outStream, connectors);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "show connectors");
    } finally {
      IOUtils.closeStream(outStream);
    }

    return 0;
  }

};