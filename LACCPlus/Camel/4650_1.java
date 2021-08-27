//,temp,HdfsAppendIT.java,109,140,temp,HdfsAppendIT.java,76,107
//,3
public class xxx {
    @Test
    public void testAppendWithDynamicFileName() throws Exception {

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start1").toF("hdfs://%s:%d/tmp/test-dynamic/?append=true&fileSystemType=HDFS",
                        service.getHDFSHost(), service.getPort());
            }
        });
        startCamelContext();

        for (int i = 0; i < ITERATIONS; ++i) {
            template.sendBodyAndHeader("direct:start1", "HELLO", Exchange.FILE_NAME, "camel-hdfs.log");
        }

        Configuration conf = new Configuration();
        String path = String.format("hdfs://%s:%d/tmp/test-dynamic/camel-hdfs.log", service.getHDFSHost(),
                service.getPort());

        Path file = new Path(path);
        FileSystem fs = FileSystem.get(file.toUri(), conf);
        FSDataInputStream in = fs.open(file);
        byte[] buffer = new byte[5];
        for (int i = 0; i < ITERATIONS; ++i) {
            assertEquals(5, in.read(buffer));
            LOG.info("> {}", new String(buffer));
        }
        int ret = in.read(buffer);
        assertEquals(-1, ret);
        in.close();
    }

};