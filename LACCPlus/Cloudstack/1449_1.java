//,temp,ApiCommand.java,551,647,temp,ApiCommand.java,464,545
//,3
public class xxx {
    public boolean verifyParam() {
        boolean result = true;
        if (this.getCommandType() == CommandType.HTTP) {
            if (this.list == false) {
                Set<?> set = verifyParam.entrySet();
                Iterator<?> it = set.iterator();

                while (it.hasNext()) {
                    Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                    String key = (String)me.getKey();
                    String value = (String)me.getValue();
                    if (value == null) {
                        s_logger.error("Parameter " + key + " is missing in the list of global parameters");
                        return false;
                    }

                    NodeList itemName = this.responseBody.getElementsByTagName(key);
                    if ((itemName.getLength() != 0) && (itemName != null)) {
                        Element itemNameElement = (Element)itemName.item(0);
                        if (itemNameElement.hasChildNodes()) {
                            continue;
                        }
                        if (!(verifyParam.get(key).equals("no value")) && !(itemNameElement.getTextContent().equals(verifyParam.get(key)))) {
                            s_logger.error("Incorrect value for the following tag: " + key + ". Expected value is " + verifyParam.get(key) + " while actual value is " +
                                itemNameElement.getTextContent());
                            result = false;
                        }
                    } else {
                        s_logger.error("Following xml element is missing in the response: " + key);
                        result = false;
                    }
                }
            }
            // for multiple elements
            else {
                Set<?> set = verifyParam.entrySet();
                Iterator<?> it = set.iterator();
                // get list element specified by id
                NodeList returnLst = this.responseBody.getElementsByTagName(this.listName.getTextContent());
                Node requiredNode = returnLst.item(Integer.parseInt(this.listId.getTextContent()));

                if (requiredNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element)requiredNode;

                    while (it.hasNext()) {
                        Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                        String key = (String)me.getKey();
                        String value = (String)me.getValue();
                        if (value == null) {
                            s_logger.error("Parameter " + key + " is missing in the list of global parameters");
                            return false;
                        }
                        NodeList itemName = fstElmnt.getElementsByTagName(key);
                        if ((itemName.getLength() != 0) && (itemName != null)) {
                            Element itemNameElement = (Element)itemName.item(0);
                            if (!(verifyParam.get(key).equals("no value")) && !(itemNameElement.getTextContent().equals(verifyParam.get(key)))) {
                                s_logger.error("Incorrect value for the following tag: " + key + ". Expected value is " + verifyParam.get(key) +
                                    " while actual value is " + itemNameElement.getTextContent());
                                result = false;
                            }
                        } else {
                            s_logger.error("Following xml element is missing in the response: " + key);
                            result = false;
                        }
                    }
                }
            }
        } else if (this.getCommandType() == CommandType.MYSQL) {
            Set<?> set = verifyParam.entrySet();
            Iterator<?> it = set.iterator();

            while (it.hasNext()) {
                Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                String key = (String)me.getKey();
                String value = (String)me.getValue();
                if (value == null) {
                    s_logger.error("Parameter " + key + " is missing in the list of global parameters");
                    return false;
                }

                String itemName = null;
                try {
                    while (this.result.next()) {
                        itemName = this.result.getString(key);
                    }
                } catch (Exception ex) {
                    s_logger.error("Unable to get element from result set " + key);
                }

                if (!(value.equals("no value")) && !(itemName.equals(verifyParam.get(key)))) {
                    s_logger.error("Incorrect value for the following tag: " + key + ". Expected value is " + verifyParam.get(key) + " while actual value is " + itemName);
                    result = false;
                }
            }
        }
        return result;
    }

};