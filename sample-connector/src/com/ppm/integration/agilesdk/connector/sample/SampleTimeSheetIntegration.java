package com.ppm.integration.agilesdk.connector.sample;


import java.util.*;

import com.ppm.integration.agilesdk.*;
import com.ppm.integration.agilesdk.pm.LinkedTaskAgileEntityInfo;
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
        Date startDate = context.currentTimeSheet().getPeriodStartDate().toGregorianCalendar().getTime();
        Date finishDate = context.currentTimeSheet().getPeriodEndDate().toGregorianCalendar().getTime();

        items.add(new SampleExternalWorkItem("Meetings",45," Release error",startDate,finishDate));
        items.add(new SampleExternalWorkItem("other",15,"TimeSheet error",startDate,finishDate));
        return items;
    }

    @Override
    public List<String> getBestTasksMatches(Map<String, LinkedTaskAgileEntityInfo> candidateTasksInfo, TimeSheetLineAgileEntityInfo timesheetLineAgileEntityInfo) {

        List<String> matchingTaskIds = new ArrayList<>();

        // We'll only return the tasks that are mapped to release 1, in no particular order.
        for (Map.Entry<String, LinkedTaskAgileEntityInfo> entry : candidateTasksInfo.entrySet()) {
            LinkedTaskAgileEntityInfo info = entry.getValue();
            if (info != null && "releaseSample 1".equalsIgnoreCase(info.getReleaseId())) {
                matchingTaskIds.add(entry.getKey());
            }
        }

        return matchingTaskIds;
    }


    private class SampleExternalWorkItem extends ExternalWorkItem {

        String name="";
        long totalEffort = 0;
        String errorMessage = null;
        Date startDate;
        Date finishDate;
        Map<String, Long> effortList = new HashMap<>();

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
        public TimeSheetLineAgileEntityInfo getLineAgileEntityInfo() {
            // All timesheet lines will be mapped to the same artificial project.
            TimeSheetLineAgileEntityInfo info = new TimeSheetLineAgileEntityInfo("sap1");
            info.setReleaseId("release1");
            info.setTeamId("team1");
            info.setSprintId("sprint1");
            info.setEpicId("epic1");
            info.setFeatureId("feature1");
            info.setBacklogItemId("bi1");
            return info;
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
