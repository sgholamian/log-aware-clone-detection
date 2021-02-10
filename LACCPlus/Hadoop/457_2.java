//,temp,RenameOp.java,80,118,temp,DeleteOp.java,56,90
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    try {
      Path fn = getDeleteFile();
      long timeTaken = 0;
      boolean deleteStatus = false;
      {
        long startTime = Timer.now();
        deleteStatus = fs.delete(fn, false);
        timeTaken = Timer.elapsed(startTime);
      }
      // collect the stats
      if (!deleteStatus) {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.FAILURES, 1L));
        LOG.info("Could not delete " + fn);
      } else {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.OK_TIME_TAKEN, timeTaken));
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.SUCCESSES, 1L));
        LOG.info("Could delete " + fn);
      }
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with deleting", e);
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with deleting", e);
    }
    return out;
  }

};