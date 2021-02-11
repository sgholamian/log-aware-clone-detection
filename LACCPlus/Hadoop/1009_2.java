//,temp,DockerClient.java,62,81,temp,TrafficController.java,624,648
//,3
public class xxx {
    public PrivilegedOperation commitBatchToTempFile()
        throws ResourceHandlerException {
      try {
        File tcCmds = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFFIX, new
            File(tmpDirPath));
        Writer writer = new OutputStreamWriter(new FileOutputStream(tcCmds),
            "UTF-8");
        PrintWriter printWriter = new PrintWriter(writer);

        for (String command : commands) {
          printWriter.println(command);
        }

        printWriter.close();
        operation.appendArgs(tcCmds.getAbsolutePath());

        return operation;
      } catch (IOException e) {
        LOG.warn("Failed to create or write to temporary file in dir: " +
            tmpDirPath);
        throw new ResourceHandlerException(
            "Failed to create or write to temporary file in dir: "
                + tmpDirPath);
      }
    }

};