//,temp,TestCheckpoint.java,682,690,temp,TestCheckpoint.java,667,675
//,3
public class xxx {
  @Test
  public void testNameNodeImageSendFailWrongSize()
      throws IOException {
    LOG.info("Starting testNameNodeImageSendFailWrongSize");
    
    Mockito.doReturn(true).when(faultInjector)
      .shouldSendShortFile(filePathContaining("fsimage"));
    doSendFailTest("is not of the advertised size");
  }

};