//,temp,CronParserTest.java,107,169,temp,CronParserTest.java,62,105
//,3
public class xxx {
    @Test
    public void testgetNextTimeDayOfWeekVariant() throws MessageFormatException {

        // using an absolute date so that result will be absolute - Monday 7 March 2011
        Calendar current = Calendar.getInstance();
        current.set(2011, Calendar.MARCH, 7, 9, 15, 30);
        LOG.debug("start:" + current.getTime());

        String test = "50 20 * * 5";
        long next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(50,result.get(Calendar.MINUTE));
        assertEquals(20,result.get(Calendar.HOUR_OF_DAY));
        // expecting Friday 11th
        assertEquals(11,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.FRIDAY,result.get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.MARCH,result.get(Calendar.MONTH));
        assertEquals(2011,result.get(Calendar.YEAR));

        // Match to the day of week, but to late to run, should just a week forward.
        current = Calendar.getInstance();
        current.set(2011, Calendar.MARCH, 11, 22, 0, 30);
        LOG.debug("update:" + current.getTime());

        next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        //assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(50,result.get(Calendar.MINUTE));
        assertEquals(20,result.get(Calendar.HOUR_OF_DAY));
        // expecting Friday 18th
        assertEquals(18,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.FRIDAY,result.get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.MARCH,result.get(Calendar.MONTH));
        assertEquals(2011,result.get(Calendar.YEAR));
    }

};