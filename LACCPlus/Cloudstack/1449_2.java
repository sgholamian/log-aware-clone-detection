//,temp,ApiCommand.java,551,647,temp,ApiCommand.java,464,545
//,3
public class xxx {
    public boolean setParam(HashMap<String, String> param) {
        if ((this.responseBody == null) && (this.commandType == CommandType.HTTP)) {
            s_logger.error("Response body is empty");
            return false;
        }
        Boolean result = true;

        if (this.getCommandType() == CommandType.MYSQL) {
            Set<?> set = this.setParam.entrySet();
            Iterator<?> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                String key = (String)me.getKey();
                String value = (String)me.getValue();
                try {
                    String itemName = null;
                    while (this.result.next()) {
                        itemName = this.result.getString(value);
                    }
                    if (itemName != null) {
                        param.put(key, itemName);
                    } else {
                        s_logger.error("Following return parameter is missing: " + value);
                        result = false;
                    }
                } catch (Exception ex) {
                    s_logger.error("Unable to set parameter " + value, ex);
                }
            }
        } else if (this.getCommandType() == CommandType.HTTP) {
            if (this.list == false) {
                Set<?> set = this.setParam.entrySet();
                Iterator<?> it = set.iterator();

                while (it.hasNext()) {
                    Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                    String key = (String)me.getKey();
                    String value = (String)me.getValue();
                    // set parameters needed for the future use
                    NodeList itemName = this.responseBody.getElementsByTagName(value);
                    if ((itemName != null) && (itemName.getLength() != 0)) {
                        for (int i = 0; i < itemName.getLength(); i++) {
                            Element itemNameElement = (Element)itemName.item(i);
                            if (itemNameElement.getChildNodes().getLength() <= 1) {
                                param.put(key, itemNameElement.getTextContent());
                                break;
                            }
                        }
                    } else {
                        s_logger.error("Following return parameter is missing: " + value);
                        result = false;
                    }
                }
            } else {
                Set<?> set = this.setParam.entrySet();
                Iterator<?> it = set.iterator();
                NodeList returnLst = this.responseBody.getElementsByTagName(this.listName.getTextContent());
                Node requiredNode = returnLst.item(Integer.parseInt(this.listId.getTextContent()));

                if (requiredNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element)requiredNode;

                    while (it.hasNext()) {
                        Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
                        String key = (String)me.getKey();
                        String value = (String)me.getValue();
                        NodeList itemName = fstElmnt.getElementsByTagName(value);
                        if ((itemName != null) && (itemName.getLength() != 0)) {
                            Element itemNameElement = (Element)itemName.item(0);
                            if (itemNameElement.getChildNodes().getLength() <= 1) {
                                param.put(key, itemNameElement.getTextContent());
                            }
                        } else {
                            s_logger.error("Following return parameter is missing: " + value);
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

};