package com.ppm.integration.agilesdk.connector.sample;

import com.ppm.integration.agilesdk.FunctionIntegration;
import com.ppm.integration.agilesdk.IntegrationConnector;
import com.ppm.integration.agilesdk.IntegrationConnectorInstance;
import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.model.AgileProject;
import com.ppm.integration.agilesdk.ui.*;

import java.util.Arrays;
import java.util.List;

public class SampleIntegrationConnector extends IntegrationConnector {

    @Override
    public String getExternalApplicationName(){
        return "Sample Connector for imaginary external application";
    }

    @Override
    public String getExternalApplicationVersionIndication(){
        return "4.2";
    }

    @Override
    public String getConnectorVersion(){
        return "1.0";
    }

    @Override
    public String getTargetApplicationIcon(){
        return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABuElEQVQ4T2NkwAGKO7a4/2f63wCSZvzH2NBb4bMTm1JGXAYEFi/5JcHHxfDh92+GY3eeMzxcUcBGkgHu2fP/gzTsuvMMrO//zmqsloEFsTkXmwHY1IENwOZcj5x57v//M+7Ydec5w3/G/x4MO6p3YlMHNgDZNg8teQYNUeEz/1kZ8q4/eHFs951XDHkuelaMvxkmPf34yeTjl28o3sIwwElRgkFcgPuvCC8vw81nr5hBBmTaaP898egZw7Vn75jtFMQxDQA59x8DQ8PuW88Z+DjZe61khVzFBHhSP3z9xXjmyXsGSUGOv5cfv5prIC28m5+DrRik7j8TQwPIWxiB+J+Bcd//v8zRjz9+lDl8+xmzhawog4QA318udrYnjMx/lzIy/HdCThsYgXjy3nMmRibW//def7htJCmk+ejjDwYjKf7rvOxsqn8Z/zFK8PH8Q04bGGEAjvP/DHkMv35Pd9eW/w0KAzvL76xsb+QzGf8zTEJPG9gN2FnNCIrzK49f7wAZUOio7QFKytjSBtgA5ECEBQ4ozjlZGZmuv/nC8O7L93+gpIxNHc68QFJSxpZJKDYAm3OxWQQAPbIyIJK9CCoAAAAASUVORK5CYII=";
    }

    @Override
    public List<AgileProject> getAgileProjects(ValueSet instanceConfigurationParameters) {
        // These "Agile Projects" will be used for Agile Data integration & Portfolio Epics integration.
        // They will be selectable in the "Advanced" tab of Integration Configuration page.
        AgileProject ap1 = new AgileProject();
        ap1.setDisplayName("My Sample Agile Project #1");
        ap1.setValue("sap1");
        AgileProject ap2 = new AgileProject();
        ap2.setDisplayName("My Sample Agile Project #2");
        ap2.setValue("sap2");
        return Arrays.asList(new AgileProject[] {ap1, ap2});
    }
    
    @Override
    public List<Field> getDriverConfigurationFields() {
        SelectList list = new SelectList(SampleConstants.APP_CLIENT_TYPE,"CLIENT_TYPE","3002",true);
        list.addLevel(SampleConstants.APP_CLIENT_TYPE, "CLIENT_TYPE");
        list.addOption(new SelectList.Option("3000","Manager"));
        list.addOption(new SelectList.Option("3001","employee"));
        list.addOption(new SelectList.Option("3002","big boss"));

        return Arrays.asList(new Field[]{
                new PlainText(SampleConstants.APP_CLIENT_ID, "CLIENT_ID", "","", true),
                new PasswordText(SampleConstants.APP_CLIENT_SECRET, "CLIENT_SECRET", "", "", true),
                new LineBreaker(),
                list,
                new LineBreaker(),
                new PlainText(SampleConstants.KEY_BASE_URL, "BASE_URL", "https://www.google.com", "",true),
                new LineBreaker(),
                new PlainText(SampleConstants.KEY_PROXY_HOST,"PROXY_HOST","","",false),
                new PlainText(SampleConstants.KEY_PROXY_PORT,"PROXY_PORT","","",false),
                new CheckBox(SampleConstants.KEY_USE_GLOBAL_PROXY,"USE_GLOBAL_PROXY","",false),
                new LineBreaker(),
            });
    }

    /**
     * This method is deprecated since PPM 9.42 and has been replaced by #getIntegrationClasses(), but we need to keep it here for backward compatibility with PPM 9.41.
     * @see IntegrationConnector#getIntegrations()
     */
    @Override
    public List<FunctionIntegration> getIntegrations() {
        return Arrays.asList(new FunctionIntegration[]{
            new SampleWorkPlanIntegration(),
            new SampleTimeSheetIntegration()
        });
    }

    @Override
    public List<String> getIntegrationClasses() {
        return Arrays.asList(new String[] {"com.ppm.integration.agilesdk.connector.sample.SampleWorkPlanIntegration","com.ppm.integration.agilesdk.connector.sample.SampleTimeSheetIntegration", "com.ppm.integration.agilesdk.connector.sample.SampleAgileDataIntegration",  "com.ppm.integration.agilesdk.connector.sample.SamplePortfolioEpicIntegration", "com.ppm.integration.agilesdk.connector.sample.SampleKpiImportIntegration"});
    }
}
