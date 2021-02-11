//,temp,CryptoUtils.java,174,195,temp,CryptoUtils.java,137,161
//,3
public class xxx {
  public static InputStream wrapIfNecessary(Configuration conf, InputStream in,
      long length) throws IOException {
    if (isEncryptedSpillEnabled(conf)) {
      int bufferSize = getBufferSize(conf);
      if (length > -1) {
        in = new LimitInputStream(in, length);
      }
      byte[] offsetArray = new byte[8];
      IOUtils.readFully(in, offsetArray, 0, 8);
      long offset = ByteBuffer.wrap(offsetArray).getLong();
      CryptoCodec cryptoCodec = CryptoCodec.getInstance(conf);
      byte[] iv = 
          new byte[cryptoCodec.getCipherSuite().getAlgorithmBlockSize()];
      IOUtils.readFully(in, iv, 0, 
          cryptoCodec.getCipherSuite().getAlgorithmBlockSize());
      if (LOG.isDebugEnabled()) {
        LOG.debug("IV read from ["
            + Base64.encodeBase64URLSafeString(iv) + "]");
      }
      return new CryptoInputStream(in, cryptoCodec, bufferSize,
          getEncryptionKey(), iv, offset + cryptoPadding(conf));
    } else {
      return in;
    }
  }

};