//,temp,ExecDocumentationExamplesTest.java,101,113,temp,ExecDocumentationExamplesTest.java,87,99
//,2
public class xxx {
    @Test
    @Disabled
    public void testWinJavaVersionWorkingDir() throws Exception {
        ExecResult body = templateJavaVersionWorkingDir.requestBody((Object) "test", ExecResult.class);
        InputStream out = body.getStdout();
        InputStream err = body.getStderr();
        // Strange that Sun Java 1.5 writes the -version in the syserr
        assertNull(out);
        assertNotNull(err);
        String outerr = IOUtils.toString(err, Charset.defaultCharset());
        LOGGER.info("Received stderr: " + outerr);
        assertTrue(outerr.contains("java version"));
    }

};