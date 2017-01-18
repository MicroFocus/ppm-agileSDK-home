package com.ppm.integration.agilesdk.connector.sample;


import java.util.*;

import com.ppm.integration.agilesdk.*;
import com.ppm.integration.agilesdk.tm.*;
import com.ppm.integration.agilesdk.ui.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SampleTimeSheetIntegration extends TimeSheetIntegration {
    @Override
    public List<Field> getMappingConfigurationFields(ValueSet paramValueSet){
        return Arrays.asList(new Field[]{
                new PlainText(SampleConstants.KEY_USERNAME,"USERNAME","admin",true),
                new PasswordText(SampleConstants.KEY_PASSWORD,"PASSWORD","admin",true)
        });
    }
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<ExternalWorkItem> getExternalWorkItems(TimeSheetIntegrationContext context, final ValueSet values) {

        final List<ExternalWorkItem> items = getExternalWorkItemsByTasks(context, values);

        return items;
    }

    public List<ExternalWorkItem> getExternalWorkItemsByTasks(TimeSheetIntegrationContext context, final ValueSet values)
    {
        final List<ExternalWorkItem> items = Collections.synchronizedList(
            new LinkedList<ExternalWorkItem>());
        Date startDate=new Date(114,02,01);
        Date finishDate=new Date(114,02,15);

        items.add(new SampleExternalWorkItem("Meetings",45," Release error",startDate,finishDate));
        items.add(new SampleExternalWorkItem("other",15,"TimeSheet error",startDate,finishDate));
        return items;
    }


    private class SampleExternalWorkItem extends ExternalWorkItem {

        String name="";
        long totalEffort = 0;
        String errorMessage = null;
        Date startDate;
        Date finishDate;
        Hashtable<String, Long> effortList = new Hashtable<>();

        public SampleExternalWorkItem(String Name,long TotalEffort,
                String ErrorMessage,Date startDate,Date finishDate) {
            this.name=Name;
            this.totalEffort=TotalEffort;
            this.errorMessage=ErrorMessage;
            this.startDate = startDate;
            this.finishDate = finishDate;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Double getTotalEffort() {
            return (double)totalEffort;
        }

        @Override
        public ExternalWorkItemEffortBreakdown getEffortBreakDown() {

            ExternalWorkItemEffortBreakdown effortBreakDown = new ExternalWorkItemEffortBreakdown();

            int numOfWorkDays = getDaysDiffNumber(startDate,finishDate);

            if (numOfWorkDays > 0) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(startDate);

                for(int i = 0; i < numOfWorkDays; i++) {
                    Long effort = effortList.get(dateFormat.format(calendar.getTime()));
                    if(effort == null) effort = 0L;
                    effortBreakDown.addEffort(calendar.getTime(), (double)totalEffort/(double)numOfWorkDays);

                    // move to next day
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }

            return effortBreakDown;
        }

        @Override
        public String getErrorMessage() {
            return errorMessage;
        }
        public int getDaysDiffNumber(Date startDate, Date endDate) {
            Calendar start = new GregorianCalendar();
            start.setTime(startDate);

            Calendar end = new GregorianCalendar();
            end.setTime(endDate);
            //move to last millsecond
            end.set(Calendar.HOUR_OF_DAY,23);
            end.set(Calendar.MINUTE,59);
            end.set(Calendar.SECOND,59);
            end.set(Calendar.MILLISECOND,999);

            Calendar dayDiff =  Calendar.getInstance();
            dayDiff.setTime(startDate);
            int diffNumber  = 0;
            while (dayDiff.before(end)) {
                diffNumber ++;
                dayDiff.add(Calendar.DAY_OF_MONTH, 1);
            }
            return diffNumber;
        }
    }
}
