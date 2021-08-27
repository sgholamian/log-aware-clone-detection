//,temp,TransactedStackSizeBreakOnExceptionTest.java,30,61,temp,TransactedStackSizeTest.java,28,61
//,3
public class xxx {
    @Test
    public void testStackSize() throws Exception {
        getMockEndpoint("mock:line").expectedMessageCount(total);
        getMockEndpoint("mock:line").assertNoDuplicates(body());
        getMockEndpoint("mock:result").expectedMessageCount(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total; i++) {
            sb.append(i);
            sb.append(",");
        }
        template.sendBody("seda:start", "" + sb.toString());

        assertMockEndpointsSatisfied();

        int[] sizes = new int[total + 1];
        for (int i = 0; i < total; i++) {
            int size = getMockEndpoint("mock:line").getReceivedExchanges().get(i).getMessage().getHeader("stackSize",
                    int.class);
            sizes[i] = size;
            Assertions.assertTrue(size < 110, "Stackframe should be < 110");
            log.debug("#{} size {}", i, size);
        }
        int size = getMockEndpoint("mock:result").getReceivedExchanges().get(0).getMessage().getHeader("stackSize", int.class);
        sizes[total] = size;
        log.debug("#{} size {}", total, size);

        int prev = sizes[0];
        // last may be shorter, so use total - 1
        for (int i = 1; i < total - 1; i++) {
            size = sizes[i];
            Assertions.assertEquals(prev, size, "Stackframe should be same size");
        }
    }

};