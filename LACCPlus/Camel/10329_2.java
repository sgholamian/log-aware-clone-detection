//,temp,ExecDocumentationExamplesTest.java,101,113,temp,ExecDocumentationExamplesTest.java,87,99
//,2
public class xxx {
    @Test
    @Disabled
    public void testJavaVersion() throws Exception {
        ExecResult body = templateJavaVersion.requestBody((Object) "test", ExecResult.class);
        InputStream out = body.getStdout();
        InputStream err = body.getStderr();
        // Strange that Sun Java 1.5 writes the -version in the syserr
        assertNull(out);
        assertNotNull(err);
        String outString = IOUtils.toString(err, Charset.defaultCharset());
        LOGGER.info("Received stdout: " + outString);
        assertTrue(outString.contains("java version"));
    }

};