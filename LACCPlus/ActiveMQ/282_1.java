//,temp,CronParserTest.java,171,192,temp,CronParserTest.java,38,60
//,3
public class xxx {
    @Test
    public void testgetNextTimeMonth() throws MessageFormatException {

        // using an absolute date so that result will be absolute - Monday 15 Nov 2010
        Calendar current = Calendar.getInstance();
        current.set(2010, Calendar.NOVEMBER, 15, 9, 15, 30);
        LOG.debug("start:" + current.getTime());

        String test = "* * * 12 *";
        long next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(0,result.get(Calendar.MINUTE));
        assertEquals(0,result.get(Calendar.HOUR_OF_DAY));
        assertEquals(1,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER,result.get(Calendar.MONTH));
        assertEquals(2010,result.get(Calendar.YEAR));
    }

};