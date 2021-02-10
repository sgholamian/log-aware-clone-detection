//,temp,CryptoUtils.java,174,195,temp,CryptoUtils.java,137,161
//,3
public class xxx {
  public static FSDataInputStream wrapIfNecessary(Configuration conf,
      FSDataInputStream in) throws IOException {
    if (isEncryptedSpillEnabled(conf)) {
      CryptoCodec cryptoCodec = CryptoCodec.getInstance(conf);
      int bufferSize = getBufferSize(conf);
      // Not going to be used... but still has to be read...
      // Since the O/P stream always writes it..
      IOUtils.readFully(in, new byte[8], 0, 8);
      byte[] iv = 
          new byte[cryptoCodec.getCipherSuite().getAlgorithmBlockSize()];
      IOUtils.readFully(in, iv, 0, 
          cryptoCodec.getCipherSuite().getAlgorithmBlockSize());
      if (LOG.isDebugEnabled()) {
        LOG.debug("IV read from Stream ["
            + Base64.encodeBase64URLSafeString(iv) + "]");
      }
      return new CryptoFSDataInputStream(in, cryptoCodec, bufferSize,
          getEncryptionKey(), iv);
    } else {
      return in;
    }
  }

};