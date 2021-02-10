//,temp,TestCheckpoint.java,682,690,temp,TestCheckpoint.java,667,675
//,3
public class xxx {
  @Test
  public void testNameNodeImageSendFailWrongDigest()
      throws IOException {
    LOG.info("Starting testNameNodeImageSendFailWrongDigest");

    Mockito.doReturn(true).when(faultInjector)
        .shouldCorruptAByte(Mockito.any(File.class));
    doSendFailTest("does not match advertised digest");
  }

};