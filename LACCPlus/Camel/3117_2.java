//,temp,LogBodyWithNewLineTest.java,73,82,temp,LogBodyWithNewLineTest.java,62,71
//,3
public class xxx {
    @Test
    public void testNoSkip() throws Exception {
        String body = "1" + LS + "2" + LS + "3";

        template.sendBody("direct:start", body);

        log.info("{}", writer);

        assertTrue(writer.toString().contains(body));
    }

};