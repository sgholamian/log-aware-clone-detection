//,temp,FileExclusiveReadNoneStrategyTest.java,52,66,temp,IrcsListUsersTest.java,60,68
//,3
public class xxx {
    @Test
    public void test() throws Exception {
        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.assertIsSatisfied();
        String body = resultEndpoint.getExchanges().get(0).getIn().getBody(String.class);
        LOGGER.debug("Received usernames: [{}]", body);
        String username = properties.getProperty("camelFrom");
        assertTrue(body.contains(username), "userlist does not contain test user");
    }

};