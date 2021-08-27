//,temp,CronParserTest.java,217,238,temp,CronParserTest.java,194,215
//,3
public class xxx {
    @Test
    public void testgetNextStartCurrMonth() throws MessageFormatException {

        // using an absolute date so that result will be absolute - Wednesday 15 Dec 2010
        Calendar current = Calendar.getInstance();
        current.set(2010, Calendar.DECEMBER, 15, 9, 15, 30);
        LOG.debug("start:" + current.getTime());

        String test = "* * 1 12 *";
        long next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(0,result.get(Calendar.MINUTE));
        assertEquals(0,result.get(Calendar.HOUR_OF_DAY));
        assertEquals(1,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER,result.get(Calendar.MONTH));
        assertEquals(2011,result.get(Calendar.YEAR));
    }

};