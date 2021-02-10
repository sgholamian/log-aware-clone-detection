//,temp,TestSSLHttpServer.java,191,212,temp,TestSSLHttpServer.java,167,185
//,3
public class xxx {
  @Test
  public void testOneEnabledCiphers() throws Exception {
    URL url = new URL(baseUrl, "/echo?a=b&c=d");
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    SSLSocketFactory sslSocketF = clientSslFactory.createSSLSocketFactory();
    PrefferedCipherSSLSocketFactory testPreferredCipherSSLSocketF
        = new PrefferedCipherSSLSocketFactory(sslSocketF,
        StringUtils.getTrimmedStrings(oneEnabledCiphers));
    conn.setSSLSocketFactory(testPreferredCipherSSLSocketF);
    assertFalse("excludedCipher list is empty", oneEnabledCiphers.isEmpty());
    try {
      InputStream in = conn.getInputStream();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      IOUtils.copyBytes(in, out, 1024);
      assertEquals(out.toString(), "a:b\nc:d\n");
      LOG.info("Atleast one additional enabled cipher than excluded ciphers,"
          + " expected successful test result.");
    } catch (SSLHandshakeException ex) {
      fail("Atleast one additional cipher available for successful handshake."
          + " Unexpected test failure: " + ex);
    }
  }

};