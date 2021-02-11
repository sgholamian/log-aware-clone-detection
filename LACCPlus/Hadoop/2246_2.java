//,temp,TestSequenceFile.java,450,458,temp,TestHistoryViewerPrinter.java,1034,1042
//,3
public class xxx {
  private String run(HistoryViewerPrinter printer) throws Exception {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(boas, true);
    printer.print(out);
    out.close();
    String outStr = boas.toString("UTF-8");
    LOG.info("out = " + outStr);
    return outStr;
  }

};