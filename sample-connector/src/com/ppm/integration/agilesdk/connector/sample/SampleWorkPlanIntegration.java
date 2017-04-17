package com.ppm.integration.agilesdk.connector.sample;

import java.lang.RuntimeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import com.ppm.integration.agilesdk.*;
import com.ppm.integration.agilesdk.pm.*;
import com.ppm.integration.agilesdk.ui.*;



public class SampleWorkPlanIntegration extends WorkPlanIntegration{


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
                new LabelText("", "NEW_RELEASE_INFORMATION","block",false),
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
            throw new RuntimeException("Error while parsing date", e);
        }
    }



    @Override
    public ExternalWorkPlan getExternalWorkPlan(WorkPlanIntegrationContext context,ValueSet values) {

        SampleTaskData smpData=new SampleTaskData();
        Date start_date=new Date(116,9,01);
        Date end_date=new Date(116,9,06);
        smpData.AddDataToFieldDict("start-date",start_date.toString());
        smpData.AddDataToFieldDict("end-date",end_date.toString());
        smpData.AddDataToFieldDict("name","Sample Task " + 3001);
        smpData.AddDataToFieldDict("id","3001");
        smpData.AddDataToFieldDict("owner-role","Dev");
        smpData.AddDataToFieldDict("owner-id","600127");
        ExternalTask.TaskStatus statue= ExternalTask.TaskStatus.IN_PLANNING;
        final SampleExternalTask workPlan=new SampleExternalTask(smpData, statue);

        SampleTaskData smpData2=new SampleTaskData();
        ExternalTask.TaskStatus statue2= ExternalTask.TaskStatus.IN_PLANNING;
        Date start_date2=new Date(116,10,01);
        Date end_date2=new Date(116,10,06);
        smpData2.AddDataToFieldDict("start-date",start_date2.toString());
        smpData2.AddDataToFieldDict("end-date",end_date2.toString());
        smpData2.AddDataToFieldDict("name","Sample Task " + 3001);
        smpData2.AddDataToFieldDict("id","3001");
        smpData2.AddDataToFieldDict("owner-role","Dev");
        smpData2.AddDataToFieldDict("owner-id","600127");
        smpData2.AddDataToFieldDict("name","Sample Task " + 3002);
        final SampleExternalTask workPlan2=new SampleExternalTask(smpData2,statue2);

        return new ExternalWorkPlan(){

            @Override
            public List<ExternalTask> getRootTasks() {
                return Arrays.asList(new ExternalTask[]{
                        workPlan,
                        workPlan2
                });
            }

        };

    }
}
