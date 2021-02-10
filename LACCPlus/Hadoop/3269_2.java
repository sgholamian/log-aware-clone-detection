//,temp,RenameOp.java,80,118,temp,TruncateOp.java,59,103
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    try {
      Path fn = getTruncateFile();
      boolean waitOnTruncate = getConfig().shouldWaitOnTruncate();
      long currentSize = fs.getFileStatus(fn).getLen();
      // determine file status for file length requirement
      // to know if should fill in partial bytes
      Range<Long> truncateSizeRange = getConfig().getTruncateSize();
      if (getConfig().shouldTruncateUseBlockSize()) {
        truncateSizeRange = getConfig().getBlockSize();
      }
      long truncateSize = Math.max(0L,
          currentSize - Range.betweenPositive(getRandom(), truncateSizeRange));
      long timeTaken = 0;
      LOG.info("Attempting to truncate file at " + fn + " to size "
          + Helper.toByteInfo(truncateSize));
      {
        // truncate
        long startTime = Timer.now();
        boolean completed = fs.truncate(fn, truncateSize);
        if(!completed && waitOnTruncate)
          waitForRecovery(fs, fn, truncateSize);
        timeTaken += Timer.elapsed(startTime);
      }
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.BYTES_WRITTEN, 0));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.OK_TIME_TAKEN, timeTaken));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.SUCCESSES, 1L));
      LOG.info("Truncate file " + fn + " to " + Helper.toByteInfo(truncateSize)
          + " in " + timeTaken + " milliseconds");
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with truncating", e);
    } catch (IOException | UnsupportedOperationException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with truncating", e);
    }
    return out;
  }

};