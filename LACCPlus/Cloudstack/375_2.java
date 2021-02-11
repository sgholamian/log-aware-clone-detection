//,temp,StressTestDirectAttach.java,280,303,temp,UriUtils.java,367,392
//,3
public class xxx {
    protected static Map<String, List<String>> getMultipleValuesFromXML(InputStream is, String[] tagNames) {
        Map<String, List<String>> returnValues = new HashMap<String, List<String>>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element rootElement = doc.getDocumentElement();
            for (int i = 0; i < tagNames.length; i++) {
                NodeList targetNodes = rootElement.getElementsByTagName(tagNames[i]);
                if (targetNodes.getLength() <= 0) {
                    s_logger.error("no " + tagNames[i] + " tag in XML response...");
                } else {
                    List<Pair<String, Integer>> priorityList = new ArrayList<>();
                    for (int j = 0; j < targetNodes.getLength(); j++) {
                        Node node = targetNodes.item(j);
                        addPriorityListElementExaminingNode(tagNames[i], node, priorityList);
                    }
                    priorityList.sort(Comparator.comparing(x -> x.second()));
                    returnValues.put(tagNames[i], getListOfFirstElements(priorityList));
                }
            }
        } catch (Exception ex) {
            s_logger.error(ex);
        }
        return returnValues;
    }

};