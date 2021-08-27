//,temp,FileExclusiveReadNoneStrategyTest.java,70,83,temp,FromFtpExclusiveReadNoneStrategyIT.java,84,100
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("Creating a slow file ...");
            File file = ftpFile("slowfile/hello.txt").toFile();
            FileOutputStream fos = new FileOutputStream(file);
            FileLock lock = fos.getChannel().lock();
            fos.write("Hello World".getBytes());
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                fos.write(("Line #" + i).getBytes());
                LOG.info("Appending to slowfile");
            }
            fos.write("Bye World".getBytes());
            lock.release();
            fos.close();
            LOG.info("... done creating slowfile");
        }

};