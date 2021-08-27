//,temp,FileExclusiveReadNoneStrategyTest.java,70,83,temp,FromFtpExclusiveReadNoneStrategyIT.java,84,100
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("Creating a slow file with no locks...");
            try (OutputStream fos = Files.newOutputStream(testFile("slowfile/hello.txt"))) {
                fos.write("Hello World".getBytes());
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(100);
                    fos.write(("Line #" + i).getBytes());
                    LOG.info("Appending to slowfile");
                }
                fos.write("Bye World".getBytes());
            }
            LOG.info("... done creating slowfile");
        }

};