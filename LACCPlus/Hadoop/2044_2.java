//,temp,JHLogAnalyzer.java,498,529,temp,JHLogAnalyzer.java,401,427
//,3
public class xxx {
    TaskHistoryLog parse(StringTokenizer tokens) throws IOException {
      while(tokens.hasMoreTokens()) {
        String t = tokens.nextToken();
        String[] keyVal = getKeyValue(t);
        if(keyVal.length < 2) continue;

        if(keyVal[0].equals("TASKID")) {
          if(TASKID == null)
            TASKID = new String(keyVal[1]);
          else if(!TASKID.equals(keyVal[1])) {
            LOG.error("Incorrect TASKID: "
                + keyVal[1].substring(0, Math.min(keyVal[1].length(), 100)) 
                + " expect " + TASKID);
            continue;
          }
        }
        else if(keyVal[0].equals("TASK_TYPE"))
          TASK_TYPE = new String(keyVal[1]);
        else if(keyVal[0].equals("TASK_STATUS"))
          TASK_STATUS = new String(keyVal[1]);
        else if(keyVal[0].equals("START_TIME"))
          START_TIME = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("FINISH_TIME"))
          FINISH_TIME = Long.parseLong(keyVal[1]);
      }
      return this;
    }

};