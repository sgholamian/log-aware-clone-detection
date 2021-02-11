//,temp,HadoopLogsAnalyzer.java,1825,1849,temp,ContainerLaunchFailAppMaster.java,59,82
//,3
public class xxx {
  public static void main(String[] args) {
    try {
      HadoopLogsAnalyzer analyzer = new HadoopLogsAnalyzer();

      int result = ToolRunner.run(analyzer, args);

      if (result == 0) {
        return;
      }

      System.exit(result);
    } catch (FileNotFoundException e) {
      LOG.error("", e);
      e.printStackTrace(staticDebugOutput);
      System.exit(1);
    } catch (IOException e) {
      LOG.error("", e);
      e.printStackTrace(staticDebugOutput);
      System.exit(2);
    } catch (Exception e) {
      LOG.error("", e);
      e.printStackTrace(staticDebugOutput);
      System.exit(3);
    }
  }

};