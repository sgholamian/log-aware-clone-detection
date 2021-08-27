//,temp,NestedMapAndListPropertyTest.java,39,71,temp,NestedMapMessageTest.java,39,74
//,3
public class xxx {
    @Override
    @SuppressWarnings("rawtypes")
    protected void assertMessageValid(int index, Message message) throws JMSException {
        assertTrue("Should be a MapMessage: " + message, message instanceof MapMessage);

        MapMessage mapMessage = (MapMessage)message;

        Object value = mapMessage.getObject("textField");
        assertEquals("textField", data[index], value);

        Map map = (Map)mapMessage.getObject("mapField");
        assertNotNull(map);
        assertEquals("mapField.a", "foo", map.get("a"));
        assertEquals("mapField.b", Integer.valueOf(23), map.get("b"));
        assertEquals("mapField.c", Long.valueOf(45), map.get("c"));

        value = map.get("d");
        assertTrue("mapField.d should be a Map", value instanceof Map);
        map = (Map)value;

        assertEquals("mapField.d.x", "abc", map.get("x"));
        value = map.get("y");
        assertTrue("mapField.d.y is a List", value instanceof List);
        List list = (List)value;
        LOG.debug("mapField.d.y: " + list);
        assertEquals("listField.size", 3, list.size());

        LOG.debug("Found map: " + map);

        list = (List)mapMessage.getObject("listField");
        LOG.debug("listField: " + list);
        assertEquals("listField.size", 3, list.size());
        assertEquals("listField[0]", "a", list.get(0));
        assertEquals("listField[1]", "b", list.get(1));
        assertEquals("listField[2]", "c", list.get(2));
    }

};