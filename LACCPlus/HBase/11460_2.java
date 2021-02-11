//,temp,MultiThreadedWriter.java,141,164,temp,MultiThreadedUpdaterWithACL.java,251,268
//,3
public class xxx {
    private void recordFailure(final Mutation m, final long keyBase,
        final long start, IOException e) {
      failedKeySet.add(keyBase);
      String exceptionInfo;
      if (e instanceof RetriesExhaustedWithDetailsException) {
        RetriesExhaustedWithDetailsException aggEx = (RetriesExhaustedWithDetailsException) e;
        exceptionInfo = aggEx.getExhaustiveDescription();
      } else {
        StringWriter stackWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stackWriter);
        e.printStackTrace(pw);
        pw.flush();
        exceptionInfo = StringUtils.stringifyException(e);
      }
      LOG.error("Failed to mutate: " + keyBase + " after " + (System.currentTimeMillis() - start)
          + "ms; region information: " + getRegionDebugInfoSafe(table, m.getRow()) + "; errors: "
          + exceptionInfo);
    }

};