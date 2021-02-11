//,temp,TestFiPipelines.java,101,120,temp,TestFiPipelines.java,78,95
//,3
public class xxx {
  @Test
  public void pipeline_04() throws IOException {
    final String METHOD_NAME = GenericTestUtils.getMethodName();
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + METHOD_NAME);
    }

    final PipelinesTestUtil.PipelinesTest pipst =
      (PipelinesTestUtil.PipelinesTest) PipelinesTestUtil.initTest();

    pipst.fiCallSetNumBytes.set(new PipelinesTestUtil.ReceivedCheckAction(METHOD_NAME));
    pipst.fiCallSetBytesAcked.set(new PipelinesTestUtil.AckedCheckAction(METHOD_NAME));

    Path filePath = new Path("/" + METHOD_NAME + ".dat");
    FSDataOutputStream fsOut = fs.create(filePath);
    TestPipelines.writeData(fsOut, 2);
    fs.close();
  }

};