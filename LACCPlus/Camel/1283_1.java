//,temp,FileExclusiveReadNoneStrategyTest.java,52,66,temp,IrcsListUsersTest.java,60,68
//,3
public class xxx {
    @Test
    public void testPollFileWhileSlowFileIsBeingWritten() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);

        // send a message to seda:start to trigger the creating of the slowfile
        // to poll
        template.sendBody("seda:start", "Create the slow file");

        mock.assertIsSatisfied();

        String body = mock.getReceivedExchanges().get(0).getIn().getBody(String.class);
        LOG.debug("Body is: " + body);
        assertFalse(body.endsWith("Bye World"), "Should not wait and read the entire file");
    }

};