//,temp,TestFiPipelines.java,101,120,temp,TestFiPipelines.java,78,95
//,3
public class xxx {
  @Test
  public void pipeline_05() throws IOException {
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
    for (int i = 0; i < 17; i++) {
      TestPipelines.writeData(fsOut, 23);
    }
    fs.close();
  } 

};