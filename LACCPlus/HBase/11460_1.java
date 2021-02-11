//,temp,MultiThreadedWriter.java,141,164,temp,MultiThreadedUpdaterWithACL.java,251,268
//,3
public class xxx {
    public void insert(Table table, Put put, long keyBase) {
      long start = System.currentTimeMillis();
      try {
        put = (Put) dataGenerator.beforeMutate(keyBase, put);
        table.put(put);
        totalOpTimeMs.addAndGet(System.currentTimeMillis() - start);
      } catch (IOException e) {
        failedKeySet.add(keyBase);
        String exceptionInfo;
        if (e instanceof RetriesExhaustedWithDetailsException) {
          RetriesExhaustedWithDetailsException aggEx = (RetriesExhaustedWithDetailsException)e;
          exceptionInfo = aggEx.getExhaustiveDescription();
        } else {
          StringWriter stackWriter = new StringWriter();
          PrintWriter pw = new PrintWriter(stackWriter);
          e.printStackTrace(pw);
          pw.flush();
          exceptionInfo = StringUtils.stringifyException(e);
        }
        LOG.error("Failed to insert: " + keyBase + " after " + (System.currentTimeMillis() - start)
            + "ms; region information: " + getRegionDebugInfoSafe(table, put.getRow())
            + "; errors: " + exceptionInfo);
      }
    }

};