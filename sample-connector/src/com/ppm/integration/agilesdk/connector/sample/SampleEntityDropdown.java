package com.ppm.integration.agilesdk.connector.sample;

import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.ui.DynamicDropdown;

import java.util.ArrayList;
import java.util.List;


public abstract class SampleEntityDropdown extends DynamicDropdown {
    public SampleEntityDropdown(String name, String labelKey, String display,
            boolean isRequired) {
        super(name, labelKey, display, isRequired);
    }

    public SampleEntityDropdown(String name, String labelKey, boolean isRequired) {
        super(name, labelKey, null, isRequired);
    }

    public abstract List<String> getDependencies();

    public abstract List<DynamicDropdown.Option> fetchDynamicalOptions(
            ValueSet paramValueSet);

    public List<DynamicDropdown.Option> getDynamicalOptions(ValueSet values) {
        try {
            return fetchDynamicalOptions(values);
        } catch (Throwable e) {
            //
        }
        return new ArrayList<DynamicDropdown.Option>(0);
    }


}
