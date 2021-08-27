//,temp,UndertowWsTwoRoutesTest.java,53,58,temp,TestClient.java,140,145
//,3
public class xxx {
        @Override
        public void onBinaryFrame(byte[] message, boolean finalFragment, int rsv) {
            received.add(message);
            LOG.info("[ws] received bytes --> " + Arrays.toString(message));
            latch.countDown();
        }

};