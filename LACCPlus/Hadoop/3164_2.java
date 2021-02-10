//,temp,TestSSLHttpServer.java,191,212,temp,TestSSLHttpServer.java,167,185
//,3
public class xxx {
  @Test
  public void testExcludedCiphers() throws Exception {
    URL url = new URL(baseUrl, "/echo?a=b&c=d");
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    SSLSocketFactory sslSocketF = clientSslFactory.createSSLSocketFactory();
    PrefferedCipherSSLSocketFactory testPreferredCipherSSLSocketF
        = new PrefferedCipherSSLSocketFactory(sslSocketF,
        StringUtils.getTrimmedStrings(excludeCiphers));
    conn.setSSLSocketFactory(testPreferredCipherSSLSocketF);
    assertFalse("excludedCipher list is empty", excludeCiphers.isEmpty());
    try {
      InputStream in = conn.getInputStream();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      IOUtils.copyBytes(in, out, 1024);
      fail("No Ciphers in common, SSLHandshake must fail.");
    } catch (SSLHandshakeException ex) {
      LOG.info("No Ciphers in common, expected succesful test result.", ex);
    }
  }

};