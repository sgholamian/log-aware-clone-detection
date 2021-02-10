//,temp,AppendOp.java,60,118,temp,ReadOp.java,62,140
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    DataInputStream is = null;
    try {
      Path fn = getReadFile();
      Range<Long> readSizeRange = getConfig().getReadSize();
      long readSize = 0;
      String readStrAm = "";
      if (getConfig().shouldReadFullFile()) {
        readSize = Long.MAX_VALUE;
        readStrAm = "full file";
      } else {
        readSize = Range.betweenPositive(getRandom(), readSizeRange);
        readStrAm = Helper.toByteInfo(readSize);
      }
      long timeTaken = 0;
      long chunkSame = 0;
      long chunkDiff = 0;
      long bytesRead = 0;
      long startTime = 0;
      DataVerifier vf = new DataVerifier();
      LOG.info("Attempting to read file at " + fn + " of size (" + readStrAm
          + ")");
      {
        // open
        startTime = Timer.now();
        is = fs.open(fn);
        timeTaken += Timer.elapsed(startTime);
        // read & verify
        VerifyOutput vo = vf.verifyFile(readSize, is);
        timeTaken += vo.getReadTime();
        chunkSame += vo.getChunksSame();
        chunkDiff += vo.getChunksDifferent();
        bytesRead += vo.getBytesRead();
        // capture close time
        startTime = Timer.now();
        is.close();
        is = null;
        timeTaken += Timer.elapsed(startTime);
      }
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.OK_TIME_TAKEN, timeTaken));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.BYTES_READ, bytesRead));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.SUCCESSES, 1L));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.CHUNKS_VERIFIED, chunkSame));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.CHUNKS_UNVERIFIED, chunkDiff));
      LOG.info("Read " + Helper.toByteInfo(bytesRead) + " of " + fn + " with "
          + chunkSame + " chunks being same as expected and " + chunkDiff
          + " chunks being different than expected in " + timeTaken
          + " milliseconds");

    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with reading", e);
    } catch (BadFileException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.BAD_FILES, 1L));
      LOG.warn("Error reading bad file", e);
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error reading", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          LOG.warn("Error closing read stream", e);
        }
      }
    }
    return out;
  }

};