package com.ppm.integration.agilesdk.connector.sample;

import com.ppm.integration.agilesdk.FunctionIntegration;
import com.ppm.integration.agilesdk.IntegrationConnector;
import com.ppm.integration.agilesdk.IntegrationConnectorInstance;
import com.ppm.integration.agilesdk.ui.*;

import java.util.Arrays;
import java.util.List;

public class SampleIntegrationConnector extends IntegrationConnector {

    @Override
    public String getExternalApplicationName(){
        return "Sample Connector";
    }

    @Override
    public String getConnectorVersion(){
        return "1.0";
    }

    @Override
    public String getTargetApplicationIcon(){
        return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAApBJREFUeNqkUs9PE0EUftPdttvttqVVW6gEf4ClBpuKGmM8EI1GOTQSPfoXGOViohdvJt5JMHrxoIkXbwRU8GgkGMPBiJVGKEqElgplodt2d9vu7K4z25baM5N8+97Oe983b948ZJom7Gex1if2DgDZAGwM+UOdgFCCOOcJuht5GYJ5ME2SaP4FQyfGAJxM1AX+W8O8i7137nR3f6T3YEhwO920wHK5Ki/9yl/6+j07oqjaM5L3oa2CJjng4x7dSsQGDd0QNnN52GzFvJFjAW/v0UDX1Myib7egQFOkIWB28RwzOjJ8crAolQRJKsL7p7G20h6OpSErOoRrl/sGp6Z/jKoVfYFs52xW1NAT8VNd/cViSZBlBWhjx1+nLMzM5qyU6BEH0FhVVYVIb6CfcvYqMHV8IdwphKRCce/EyU+yRRiKixDkNyC9UoBUSgKGZcDn84coh6S9qF/BwN2MDXhd1wFjHaRiGRS1AmejHNy+7ofUqgITHxWwsU6gj65pmKecVg8MjDDGSK1ikEoVMAwEiGHh8Z0eK/zk1SawTmGvOgOxiHJaAjrOiDuqItcYgXG4gWkk3njwx7IO3t/WUK1SUyiH+o0m4vn1NXGL473g4DwWEBmsuZdnLFC/uU9REEt5ymkJ6HhyfXk1TWqT7ZwX7E4PsA6+Na7Ep3sUWAN5ez2zTDmtK5hGrqYq48nZWd/A0NWYy9PhZjkBroz+tsKeQ8ctq0i78tKXuSSuKONk7HPtk2ga00o+i769fXM/HI2fCPYNHPAFI646cUfdWlkUN34upLVadQxx3mlodArRoWEOPyfX0MBUC2BW5DAZjJskdpGgpyG/RvCZPM0E4twbyNVB+HbQs3frAvtZ/wQYAIlLIa+ciRrSAAAAAElFTkSuQmCC";
    }
    @SuppressWarnings("deprecation")
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
                new PlainText(SampleConstants.KEY_BASE_URL, "BASE_URL", "https://agilemanager-int.saas.hp.com", "",true),
                new LineBreaker(),
                new PlainText(SampleConstants.KEY_PROXY_HOST,"PROXY_HOST","","",false),
                new PlainText(SampleConstants.KEY_PROXY_PORT,"PROXY_PORT","","",false),
                new CheckBox(SampleConstants.KEY_USE_GLOBAL_PROXY,"USE_GLOBAL_PROXY","",false),
                new LineBreaker(),
            });
    }

    @Override
    public List<FunctionIntegration> getIntegrations() {
        return Arrays.asList(new FunctionIntegration[]{
            new SampleWorkPlanIntegration(),
            new SampleTimeSheetIntegration()
        });
    }
}
