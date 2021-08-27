//,temp,ListIndexTest.java,643,721,temp,ListIndexTest.java,539,605
//,3
public class xxx {
    @Test(timeout=60000)
    public void testListLargeDataAddAndNonSequentialRemove() throws Exception {

        final int NUM_ITERATIONS = 100;

        pf = new PageFile(getDirectory(), getClass().getName());
        pf.setPageSize(4*1024);
        pf.setEnablePageCaching(false);
        pf.setWriteBatchSize(1);
        pf.load();
        tx = pf.tx();
        long id = tx.allocate().getPageId();

        ListIndex<String, SequenceSet> test = new ListIndex<String, SequenceSet>(pf, id);
        test.setKeyMarshaller(StringMarshaller.INSTANCE);
        test.setValueMarshaller(SequenceSet.Marshaller.INSTANCE);
        test.load(tx);
        tx.commit();

        int expectedListEntries = 0;
        int nextSequenceId = 0;

        LOG.info("Loading up the ListIndex with "+NUM_ITERATIONS+" entires and sparsely populating the sequences.");

        for (int i = 0; i < NUM_ITERATIONS; ++i) {
            test.add(tx, String.valueOf(expectedListEntries++), new SequenceSet());

            for (int j = 0; j < expectedListEntries; j++) {

                SequenceSet sequenceSet = test.get(tx, String.valueOf(j));

                int startSequenceId = nextSequenceId;
                for (int ix = 0; ix < NUM_ITERATIONS; ix++) {
                    sequenceSet.add(nextSequenceId++);
                    test.put(tx, String.valueOf(j), sequenceSet);
                }

                sequenceSet = test.get(tx, String.valueOf(j));

                for (int ix = 0; ix < NUM_ITERATIONS - 1; ix++) {
                    sequenceSet.remove(startSequenceId++);
                    test.put(tx, String.valueOf(j), sequenceSet);
                }
            }
        }

        LOG.info("Checking if Index has the expected number of entries.");

        for (int i = 0; i < NUM_ITERATIONS; ++i) {
            assertTrue("List should contain Key["+i+"]",test.containsKey(tx, String.valueOf(i)));
            assertNotNull("List contents of Key["+i+"] should not be null", test.get(tx, String.valueOf(i)));
        }

        LOG.info("Index has the expected number of entries.");

        assertEquals(expectedListEntries, test.size());

        for (int i = 0; i < NUM_ITERATIONS; i += 2) {
            LOG.debug("Size of ListIndex before removal of entry ["+i+"] is: " + test.size());

            assertTrue("List should contain Key["+i+"]",test.containsKey(tx, String.valueOf(i)));
            assertNotNull("List contents of Key["+i+"] should not be null", test.remove(tx, String.valueOf(i)));
            LOG.debug("Size of ListIndex after removal of entry ["+i+"] is: " + test.size());

            assertEquals(--expectedListEntries, test.size());
        }

        for (int i = NUM_ITERATIONS - 1; i > 0; i -= 2) {
            LOG.debug("Size of ListIndex before removal of entry ["+i+"] is: " + test.size());

            assertTrue("List should contain Key["+i+"]",test.containsKey(tx, String.valueOf(i)));
            assertNotNull("List contents of Key["+i+"] should not be null", test.remove(tx, String.valueOf(i)));
            LOG.debug("Size of ListIndex after removal of entry ["+i+"] is: " + test.size());

            assertEquals(--expectedListEntries, test.size());
        }

        assertEquals(0, test.size());
    }

};