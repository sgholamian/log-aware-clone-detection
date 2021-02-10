//,temp,DeleteOp.java,56,90,temp,ListOp.java,60,91
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    try {
      Path dir = getDirectory();
      long dirEntries = 0;
      long timeTaken = 0;
      {
        long startTime = Timer.now();
        FileStatus[] files = fs.listStatus(dir);
        timeTaken = Timer.elapsed(startTime);
        dirEntries = files.length;
      }
      // log stats
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.OK_TIME_TAKEN, timeTaken));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.SUCCESSES, 1L));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.DIR_ENTRIES, dirEntries));
      LOG.info("Directory " + dir + " has " + dirEntries + " entries");
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with listing", e);
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with listing", e);
    }
    return out;
  }

};