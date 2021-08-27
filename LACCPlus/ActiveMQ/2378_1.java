//,temp,CronParserTest.java,107,169,temp,CronParserTest.java,62,105
//,3
public class xxx {
    @Test
    public void testgetNextTimeMonthVariant() throws MessageFormatException {

        // using an absolute date so that result will be absolute - Monday 7 March 2011
        Calendar current = Calendar.getInstance();
        current.set(2011, Calendar.MARCH, 7, 9, 15, 30);
        LOG.debug("start:" + current.getTime());

        String test = "0 20 * 4,5 0";
        long next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(0,result.get(Calendar.MINUTE));
        assertEquals(20,result.get(Calendar.HOUR_OF_DAY));
        // expecting Sunday 3rd of April
        assertEquals(Calendar.APRIL,result.get(Calendar.MONTH));
        assertEquals(3,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.SUNDAY,result.get(Calendar.DAY_OF_WEEK));
        assertEquals(2011,result.get(Calendar.YEAR));

        current = Calendar.getInstance();
        current.set(2011, Calendar.APRIL, 30, 22, 0, 30);
        LOG.debug("update:" + current.getTime());

        next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(0,result.get(Calendar.MINUTE));
        assertEquals(20,result.get(Calendar.HOUR_OF_DAY));
        // expecting Sunday 1st of May
        assertEquals(1,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.SUNDAY,result.get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.MAY,result.get(Calendar.MONTH));
        assertEquals(2011,result.get(Calendar.YEAR));

        // Move past last time and see if reschedule to next year works.
        current = Calendar.getInstance();
        current.set(2011, Calendar.MAY, 30, 22, 0, 30);
        LOG.debug("update:" + current.getTime());

        next = CronParser.getNextScheduledTime(test, current.getTimeInMillis());

        result = Calendar.getInstance();
        result.setTimeInMillis(next);
        LOG.debug("next:" + result.getTime());

        assertEquals(0,result.get(Calendar.SECOND));
        assertEquals(0,result.get(Calendar.MINUTE));
        assertEquals(20,result.get(Calendar.HOUR_OF_DAY));
        // expecting Sunday 1st of April - 2012
        assertEquals(1,result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.SUNDAY,result.get(Calendar.DAY_OF_WEEK));
        assertEquals(Calendar.APRIL,result.get(Calendar.MONTH));
        assertEquals(2012,result.get(Calendar.YEAR));
    }

};