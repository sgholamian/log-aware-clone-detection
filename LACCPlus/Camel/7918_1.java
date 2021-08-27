//,temp,Olingo4AppAPITest.java,314,322,temp,Olingo4AppAPITest.java,184,194
//,3
public class xxx {
    @Test
    public void testReadCount() throws Exception {
        final TestOlingo4ResponseHandler<Long> countHandler = new TestOlingo4ResponseHandler<>();

        olingoApp.read(edm, PEOPLE + COUNT_OPTION, null, null, countHandler);
        Long count = countHandler.await();
        assertEquals(20, count.intValue());
        LOG.info("People count: {}", count);
    }

};