//,temp,TestAuthenticationSessionCookie.java,159,179,temp,TestAuthenticationSessionCookie.java,137,157
//,3
public class xxx {
  @Test
  public void testSessionCookie() throws IOException {
    try {
        startServer(true);
    } catch (Exception e) {
        // Auto-generated catch block
        e.printStackTrace();
    }

    URL base = new URL("http://" + NetUtils.getHostPortString(server
            .getConnectorAddress(0)));
    HttpURLConnection conn = (HttpURLConnection) new URL(base,
            "/echo").openConnection();

    String header = conn.getHeaderField("Set-Cookie");
    List<HttpCookie> cookies = HttpCookie.parse(header);
    Assert.assertTrue(!cookies.isEmpty());
    Log.info(header);
    Assert.assertFalse(header.contains("; Expires="));
    Assert.assertTrue("token".equals(cookies.get(0).getValue()));
  }

};