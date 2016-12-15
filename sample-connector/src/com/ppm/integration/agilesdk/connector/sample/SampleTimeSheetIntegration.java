package com.ppm.integration.agilesdk.connector.sample;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.hp.ppm.integration.ValueSet;
import com.hp.ppm.integration.tm.ExternalWorkItemActualEfforts;
import com.hp.ppm.integration.tm.IExternalWorkItem;
import com.hp.ppm.integration.tm.TimeSheetIntegration;
import com.hp.ppm.integration.tm.TimeSheetIntegrationContext;
import com.hp.ppm.integration.ui.Field;
import com.hp.ppm.integration.ui.PasswordText;
import com.hp.ppm.integration.ui.PlainText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;

public class SampleTimeSheetIntegration implements TimeSheetIntegration{
	@Override
	public  List<Field> getMappingConfigurationFields(ValueSet paramValueSet){
		return Arrays.asList(new Field[]{
				new PlainText(SampleConstants.KEY_USERNAME,"USERNAME","admin",true),
				new PasswordText(SampleConstants.KEY_PASSWORD,"PASSWORD","admin",true)
		});
	}
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<IExternalWorkItem> getExternalWorkItems(TimeSheetIntegrationContext context, final ValueSet values) {

		final List<IExternalWorkItem> items = getExternalWorkItemsByTasks(context, values);

		return items;
	}

	public List<IExternalWorkItem> getExternalWorkItemsByTasks(TimeSheetIntegrationContext context, final ValueSet values) 
	{
		final List<IExternalWorkItem> items = Collections.synchronizedList(
			new LinkedList<IExternalWorkItem>());
		Date startDate=new Date(114,02,01);
		Date finishDate=new Date(114,02,15);
		
		items.add(new SampleExternalWorkItem("Meetings",45," Release error",startDate,finishDate));
		items.add(new SampleExternalWorkItem("other",15,"TimeSheet error",startDate,finishDate));
		return items;
	}


	private class SampleExternalWorkItem implements IExternalWorkItem {

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
		public double getEffort() {
			return totalEffort;
		}

		@Override
		public String getExternalData() {


			JSONObject json = new JSONObject();
			

			json.put("serverURL", " ");
			json.put("username", "sample@hpe");
			json.put("password", "********");

			json.put("domain", "");
			json.put("project", "");
			json.put("release", "");
			json.put("effort",this.getEffort());
			json.put("errorMessage", "");

			int numOfWorkDays =getDaysDiffNumber(startDate,finishDate);

			if (numOfWorkDays > 0) {
				ExternalWorkItemActualEfforts actual = new ExternalWorkItemActualEfforts();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(startDate);

				for(int i = 0; i < numOfWorkDays; i++) {
					Long effort = effortList.get(dateFormat.format(calendar.getTime()));
					if(effort == null) effort = 0L;
					actual.getEffortList().put(ExternalWorkItemActualEfforts.dateFormat.format(calendar.getTime()), (double)totalEffort/(double)numOfWorkDays);
					// move to next day
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}
				json.put(ExternalWorkItemActualEfforts.JSON_KEY_FOR_ACTUAL_EFFORT, actual.toJson());
			}

			return json.toString();
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
