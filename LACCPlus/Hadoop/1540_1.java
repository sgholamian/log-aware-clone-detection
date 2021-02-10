//,temp,PipelinesTestUtil.java,94,113,temp,PipelinesTestUtil.java,54,73
//,3
public class xxx {
    public void run(NodeBytes nb) throws IOException {
      synchronized (ack) {
        ack.add(nb);
        for (NodeBytes n : ack) {
          NodeBytes counterPart = null;
          long counterPartsBytes = -1;
          if (rcv.size() > ack.indexOf(n)) { 
            counterPart = rcv.get(ack.indexOf(n));
            counterPartsBytes = counterPart.bytes;
          }
          assertTrue("FI: Wrong acknowledged length",
              counterPartsBytes == n.bytes);
          if(FiTestUtil.LOG.isDebugEnabled()) {
            FiTestUtil.LOG.debug(
                "FI: before compare of Acked bytes. Expected " +
                n.bytes + ", got " + counterPartsBytes);
          }
        }
      }
    }

};