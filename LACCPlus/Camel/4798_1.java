//,temp,FtpPollEnrichConsumeWithDisconnectAndDeleteIT.java,35,73,temp,SftpPollEnrichConsumeWithDisconnectAndDeleteIT.java,39,78
//,3
public class xxx {
    @Test
    public void testFtpSimpleConsume() throws Exception {
        String expected = "Hello World";

        // create file using regular file
        template.sendBodyAndHeader("file://{{ftp.root.dir}}/poll", expected, Exchange.FILE_NAME, "hello.txt");

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.expectedHeaderReceived(Exchange.FILE_NAME, "hello.txt");
        mock.expectedBodiesReceived(expected);

        ProducerTemplate triggerTemplate = context.createProducerTemplate();
        triggerTemplate.sendBody("vm:trigger", "");

        assertMockEndpointsSatisfied();

        long startFileDeletionCheckTime = System.currentTimeMillis();
        boolean fileExists = true;
        while (System.currentTimeMillis() - startFileDeletionCheckTime < 3000) { // wait
                                                                                // up
                                                                                // to
                                                                                // 3000ms
                                                                                // for
                                                                                // file
                                                                                // to
                                                                                // be
                                                                                // deleted
            File file = ftpFile("poll/hello.txt").toFile();
            fileExists = file.exists();

            if (fileExists) {
                LOG.info("Will check that file has been deleted again in 200ms");
                Thread.sleep(200);
            }
        }

        assertFalse(fileExists, "The file should have been deleted");
    }

};