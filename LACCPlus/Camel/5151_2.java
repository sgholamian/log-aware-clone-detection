//,temp,XMLNoSplitRowsTest.java,49,105,temp,XMLSplitRowsTest.java,52,105
//,3
public class xxx {
    @Test
    public void testHeaderAndTrailer() throws Exception {
        results.expectedMessageCount(6);
        results.assertIsSatisfied();

        int counter = 0;
        List<Exchange> list = results.getReceivedExchanges();

        // assert header
        Element header = list.get(0).getIn().getBody(Document.class).getDocumentElement();
        NodeList headerNodes = header.getElementsByTagName("Column");
        for (int i = 0; i < headerNodes.getLength(); i++) {
            Element column = (Element) headerNodes.item(i);
            if (column.getAttribute("name").equals("INDICATOR")) {
                assertEquals("HBT", column.getTextContent());
            } else if (column.getAttribute("name").equals("DATE")) {
                assertEquals("20080817", column.getTextContent());
            } else {
                fail("Invalid Header Field");
            }
        }

        // assert body
        for (Exchange exchange : list.subList(1, 5)) {
            Message in = exchange.getIn();
            Element record = in.getBody(Document.class).getDocumentElement();
            NodeList columnNodes = record.getElementsByTagName("Column");
            boolean firstNameFound = false;
            for (int i = 0; i < columnNodes.getLength(); i++) {
                Element column = (Element) columnNodes.item(i);
                if (column.getAttribute("name").equals("FIRSTNAME")) {
                    assertEquals(expectedFirstName[counter], column.getTextContent());
                    firstNameFound = true;
                }
            }
            assertTrue(firstNameFound);
            LOG.info("Result: " + counter + " = " + record);
            counter++;
        }

        // assert trailer
        Element trailer = list.get(5).getIn().getBody(Document.class).getDocumentElement();
        NodeList trailerNodes = trailer.getElementsByTagName("Column");
        for (int i = 0; i < trailerNodes.getLength(); i++) {
            Element column = (Element) trailerNodes.item(i);
            if (column.getAttribute("name").equals("INDICATOR")) {
                assertEquals("FBT", column.getTextContent());
            } else if (column.getAttribute("name").equals("STATUS")) {
                assertEquals("SUCCESS", column.getTextContent());
            } else {
                fail("Invalid Trailer Field");
            }
        }
    }

};