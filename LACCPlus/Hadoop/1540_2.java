//,temp,PipelinesTestUtil.java,94,113,temp,PipelinesTestUtil.java,54,73
//,3
public class xxx {
    @Override
    public void run(NodeBytes nb) throws IOException {
      synchronized (rcv) {
        rcv.add(nb);
        for (NodeBytes n : rcv) {
          long counterPartsBytes = -1;
          NodeBytes counterPart = null;
          if (ack.size() > rcv.indexOf(n)) {
            counterPart = ack.get(rcv.indexOf(n));
            counterPartsBytes = counterPart.bytes;
          }
          assertTrue("FI: Wrong receiving length",
              counterPartsBytes <= n.bytes);
          if(FiTestUtil.LOG.isDebugEnabled()) {
            FiTestUtil.LOG.debug("FI: before compare of Recv bytes. Expected "
                + n.bytes + ", got " + counterPartsBytes);
          }
        }
      }
    }

};