//,temp,XMLNoSplitRowsTest.java,49,105,temp,XMLSplitRowsTest.java,52,105
//,3
public class xxx {
    @Test
    public void testHeaderAndTrailer() throws Exception {
        results.expectedMessageCount(1);
        results.message(0).body().isInstanceOf(Document.class);
        results.message(0).header("camelFlatpackCounter").isEqualTo(6);

        results.assertIsSatisfied();

        Document data = results.getExchanges().get(0).getIn().getBody(Document.class);
        Element docElement = data.getDocumentElement();
        assertEquals("Dataset", docElement.getTagName());

        // assert header
        Element header = (Element) docElement.getElementsByTagName("DatasetHeader").item(0);
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
        NodeList list = docElement.getElementsByTagName("DatasetRecord");
        for (int counter = 0; counter < list.getLength(); counter++) {
            Element record = (Element) list.item(counter);
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
        }

        // assert trailer
        Element trailer = (Element) docElement.getElementsByTagName("DatasetTrailer").item(0);
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