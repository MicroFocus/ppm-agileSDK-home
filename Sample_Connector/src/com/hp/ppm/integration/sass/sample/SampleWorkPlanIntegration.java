package com.hp.ppm.integration.sass.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hp.ppm.integration.ValueSet;
import com.hp.ppm.integration.pm.IExternalTask;
import com.hp.ppm.integration.pm.IExternalWorkPlan;
import com.hp.ppm.integration.pm.ITaskActual;
import com.hp.ppm.integration.pm.WorkPlanIntegration;
import com.hp.ppm.integration.pm.WorkPlanIntegrationContext;
import com.hp.ppm.integration.pm.IExternalTask.TaskStatus;
import com.hp.ppm.integration.ui.CheckBox;
import com.hp.ppm.integration.ui.DatePicker;
import com.hp.ppm.integration.ui.Field;
import com.hp.ppm.integration.ui.FieldAppearance;
import com.hp.ppm.integration.ui.LableText;
import com.hp.ppm.integration.ui.LineBreaker;
import com.hp.ppm.integration.ui.LineHr;
import com.hp.ppm.integration.ui.NumberText;
import com.hp.ppm.integration.ui.PasswordText;
import com.hp.ppm.integration.ui.PlainText;



public class SampleWorkPlanIntegration implements  WorkPlanIntegration{

	
	public FieldAppearance getCreateReleaseUselessFieldsAppearance(ValueSet values)
    {
    	FieldAppearance option = new FieldAppearance();
		if (!values.isAllSet(SampleConstants.KEY_CREATE_RELEASE)) {
			return null;
		}
		
		String isCreateRelease = values.get(SampleConstants.KEY_CREATE_RELEASE);
		if (isCreateRelease.equals("false")) {
			
			option = new FieldAppearance("required", "disabled");

		} else if (isCreateRelease.equals("true")) {
			
			option = new FieldAppearance("disabled", "required");
		}

		return option;
    }
	public FieldAppearance getCreateReleaseFieldsAppearance(ValueSet values)
    {
    	FieldAppearance option = new FieldAppearance();
		if (!values.isAllSet(SampleConstants.KEY_CREATE_RELEASE)) {
			return null;
		}

		String isCreateRelease = values.get(SampleConstants.KEY_CREATE_RELEASE);
		if (isCreateRelease.equals("false")) {

			option = new FieldAppearance("disabled", "required");

		} else if (isCreateRelease.equals("true")) {

			option = new FieldAppearance("required", "disabled");
		}

		return option;
    }
    
	@Override
	public List<Field> getMappingConfigurationFields(WorkPlanIntegrationContext context,ValueSet values) {
		String strTemp="yyyy-MM-dd HH:mm:ss";
		try {
			java.util.Date endd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2009-10-10 00:00:00");
		
		    return Arrays.asList(new Field[]{
				new PlainText(SampleConstants.KEY_USERNAME,"USERNAME","","block",true),
				new PasswordText(SampleConstants.KEY_PASSWORD,"PASSWORD","","block",true),
				new LineBreaker(),
				new SampleEntityDropdown(SampleConstants.KEY_RELEASE,"RELEASE","block",true){
					
					@Override
					public List<String> getStyleDependencies() {
						return Arrays.asList(new String[] { SampleConstants.KEY_CREATE_RELEASE });
					}

					@Override
					public FieldAppearance getFieldAppearance(ValueSet values) {
						return getCreateReleaseUselessFieldsAppearance(values);
					}


					@Override
					public List<String> getDependencies(){
						return Arrays.asList(new String[]{
								SampleConstants.KEY_USERNAME,
								SampleConstants.KEY_PASSWORD,
						});
					}

	                @Override
	                public List<Option> fetchDynamicalOptions(ValueSet values) {

						if(!values.isAllSet(SampleConstants.KEY_BASE_URL)){
							return null;
						}
						List<Option> options = new ArrayList<Option>(5);
						options.add(new Option("releaseSample 1","Release Sample 922"));
						options.add(new Option("releaseSample 2","Release Sample 932"));
						options.add(new Option("releaseSample 3","Release Sample 940"));
						options.add(new Option("releaseSample 4","Release Sample 941"));
						
						return options;
					}
	            },
	            new LineBreaker(),
	            new LineBreaker(),
	            new LineHr(),
	            new LableText("", "NEW_RELEASE_INFORMATION","block",false),
	            new LineBreaker(),
				new CheckBox(SampleConstants.KEY_CREATE_RELEASE,"IS_CREATE_RELEASE","block",false),
				new LineBreaker(),
				new PlainText(SampleConstants.KEY_NAME,"CREATE_RELEASE_NAME","","block",false)
				{
	                	@Override
						public List<String> getStyleDependencies() {
							return Arrays.asList(new String[] { SampleConstants.KEY_CREATE_RELEASE });
						}

						@Override
						public FieldAppearance getFieldAppearance(ValueSet values) {
							return getCreateReleaseFieldsAppearance(values);
						}
							
				},
				new DatePicker(SampleConstants.KEY_START_TIME,"CREATE_RELEASE_START_TIME",new SimpleDateFormat(strTemp).format(endd),"block",false)
				{
					@Override
					public List<String> getStyleDependencies() {
						return Arrays.asList(new String[] { SampleConstants.KEY_CREATE_RELEASE });
					}

					@Override
					public FieldAppearance getFieldAppearance(ValueSet values) {
						return getCreateReleaseFieldsAppearance(values);

					}
				},
				new LineBreaker(),
				new NumberText(SampleConstants.KEY_SPRINT_DURATION,"CREATE_RELEASE_SPRINT_DURATION","","block",false)
				{
					@Override
					public List<String> getStyleDependencies() {
						return Arrays.asList(new String[] { SampleConstants.KEY_CREATE_RELEASE });
					}

					@Override
					public FieldAppearance getFieldAppearance(ValueSet values) {
						return getCreateReleaseFieldsAppearance(values);

					}
				},

				
			});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean linkTaskWithExternal(WorkPlanIntegrationContext context,ValueSet values) {
		return false;
	}
	

	@Override
	public IExternalWorkPlan getExternalWorkPlan(WorkPlanIntegrationContext context,ValueSet values) {

		SampleTaskData smpData=new SampleTaskData();
		Date start_date=new Date(116,9,01);
		Date end_date=new Date(116,9,06);
		smpData.AddDataToFieldDict("start-date",start_date.toString());
		smpData.AddDataToFieldDict("end-date",end_date.toString());
		smpData.AddDataToFieldDict("name","Sample Task " + 3001);
		smpData.AddDataToFieldDict("id","3001");
		smpData.AddDataToFieldDict("owner-role","Dev");
		smpData.AddDataToFieldDict("owner-id","600127");
		TaskStatus statue=TaskStatus.IN_PLANNING;
		final SampleIExternalTask workPlan=new SampleIExternalTask(smpData,statue);
		
		SampleTaskData smpData2=new SampleTaskData();
		TaskStatus statue2=TaskStatus.IN_PLANNING;
		Date start_date2=new Date(116,10,01);
		Date end_date2=new Date(116,10,06);
		smpData2.AddDataToFieldDict("start-date",start_date2.toString());
		smpData2.AddDataToFieldDict("end-date",end_date2.toString());
		smpData2.AddDataToFieldDict("name","Sample Task " + 3001);
		smpData2.AddDataToFieldDict("id","3001");
		smpData2.AddDataToFieldDict("owner-role","Dev");
		smpData2.AddDataToFieldDict("owner-id","600127");
		smpData2.AddDataToFieldDict("name","Sample Task " + 3002);
		final SampleIExternalTask workPlan2=new SampleIExternalTask(smpData2,statue2);
		
		//
		return new IExternalWorkPlan(){

			@Override
			public List<IExternalTask> getRootTasks() {
				return Arrays.asList(new IExternalTask[]{
						workPlan,
						workPlan2
				});
			}

		};

	}

	@Override
	public boolean unlinkTaskWithExternal(WorkPlanIntegrationContext context,ValueSet values) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getCustomDetailPage() {
		// TODO Auto-generated method stub
		return null;//"/itg/integrationcenter/agm-connector-impl-web/agm-graphs.jsp";
	}


}
