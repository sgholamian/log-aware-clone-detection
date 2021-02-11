//,temp,ServerAdminClient.java,138,169,temp,ServerAdminClient.java,36,68
//,2
public class xxx {
    public static void kill(String host, int port) {
        Socket s = null;
        try {
            byte[] reqBytes = new byte[4];
            ByteBuffer req = ByteBuffer.wrap(reqBytes);
            req.putInt(ByteBuffer.wrap("kill".getBytes()).getInt());
            s = new Socket();
            s.setSoLinger(false, 10);
            s.setSoTimeout(20000);
            s.connect(new InetSocketAddress(host, port));

            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            os.write(reqBytes);
            byte[] resBytes = new byte[4];

            int rc = is.read(resBytes);
            String retv = new String(resBytes);
            System.out.println("rc=" + rc + " retv=" + retv);
        } catch (IOException e) {
            LOG.warn("Unexpected exception", e);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    LOG.warn("Unexpected exception", e);
                }
            }
        }
    }

};