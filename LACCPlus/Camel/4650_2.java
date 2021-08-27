//,temp,HdfsAppendIT.java,109,140,temp,HdfsAppendIT.java,76,107
//,3
public class xxx {
    @Test
    public void testAppend() throws Exception {
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start1")
                        .toF("hdfs://%s:%d/tmp/test/test-camel-simple-write-file1?append=true&fileSystemType=HDFS",
                                service.getHDFSHost(), service.getPort());
            }
        });
        startCamelContext();

        for (int i = 0; i < 10; ++i) {
            template.sendBody("direct:start1", "PIPPQ");
        }

        Configuration conf = new Configuration();
        String path = String.format("hdfs://%s:%d/tmp/test/test-camel-simple-write-file1", service.getHDFSHost(),
                service.getPort());
        Path file = new Path(path);
        FileSystem fs = FileSystem.get(file.toUri(), conf);
        FSDataInputStream in = fs.open(file);
        byte[] buffer = new byte[5];
        int ret = 0;
        for (int i = 0; i < 20; ++i) {
            ret = in.read(buffer);
            LOG.info("> {}", new String(buffer));
        }
        ret = in.read(buffer);
        assertEquals(-1, ret);
        in.close();
    }

};