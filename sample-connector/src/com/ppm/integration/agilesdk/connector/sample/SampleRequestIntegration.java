package com.ppm.integration.agilesdk.connector.sample;

import com.hp.ppm.integration.model.AgileEntityFieldValue;
import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.dm.*;
import com.ppm.integration.agilesdk.dm.DataField.DATA_TYPE;
import com.ppm.integration.agilesdk.dm.User;
import com.ppm.integration.agilesdk.model.AgileEntity;
import com.ppm.integration.agilesdk.model.AgileEntityFieldInfo;
import com.ppm.integration.agilesdk.model.AgileEntityInfo;
import com.ppm.integration.agilesdk.provider.Providers;
import com.ppm.integration.agilesdk.provider.UserProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This class provides {@link RequestIntegration} features to synchronize a PPM Request with an Agile entity (Story, Epic, etc.).
 * All the data returned here is fake and for educational purposes only; in a real connector, you would retrieve all the information from the Agile tools, for example through a REST API.
 */
public class SampleRequestIntegration extends RequestIntegration {

    @Override public List<AgileEntityInfo> getAgileEntitiesInfo(String agileProjectValue,
            ValueSet instanceConfigurationParameters)
    {
        // We will claim that our agile tool can sync with Epics, Stories and Features.
        List<AgileEntityInfo> entitiesInfo = new ArrayList<>();

        AgileEntityInfo epic = new AgileEntityInfo();
        epic.setName("Epic");
        epic.setType("EPIC");
        entitiesInfo.add(epic);

        AgileEntityInfo feature = new AgileEntityInfo();
        feature.setName("Feature");
        feature.setType("FEATURE");
        entitiesInfo.add(feature);

        AgileEntityInfo story = new AgileEntityInfo();
        story.setName("Story");
        story.setType("STORY");
        entitiesInfo.add(story);

        return entitiesInfo;
    }
    

    @Override public List<AgileEntityFieldInfo> getAgileEntityFieldsInfo(String agileProjectValue, String entityType,
            ValueSet instanceConfigurationParameters)
    {
        // For any entity, we'll expose fields name (string), story points (integer), assignee (User), and priority (ListNode).

        // The result of this method is used to list fields that can be mapped for each entity in the Configuration UI.

        // The "fieldType" is just a text that will be displayed in the fields mapping config UI to help
        // admins figure out the types of the fields. There's no real validation of the type.
        List<AgileEntityFieldInfo> fields = new ArrayList<>();

        AgileEntityFieldInfo nameField = new AgileEntityFieldInfo();
        nameField.setLabel("Name");
        nameField.setListType(false);
        nameField.setId("NAME_FIELD");
        nameField.setFieldType(DATA_TYPE.STRING.name());
        fields.add(nameField);

        AgileEntityFieldInfo storyPointsField = new AgileEntityFieldInfo();
        storyPointsField.setLabel("Story Points");
        storyPointsField.setListType(false);
        storyPointsField.setId("STORY_POINTS_FIELD");
        storyPointsField.setFieldType(DATA_TYPE.INTEGER.name());
        fields.add(storyPointsField);

        AgileEntityFieldInfo assigneeField = new AgileEntityFieldInfo();
        assigneeField.setLabel("Assignee");
        assigneeField.setListType(false);
        assigneeField.setId("ASSIGNEE_FIELD");
        assigneeField.setFieldType(DATA_TYPE.USER.name());
        fields.add(assigneeField);
        
        AgileEntityFieldInfo priority = new AgileEntityFieldInfo();
        priority.setLabel("Priority");
        priority.setListType(true);
        priority.setId("PRIORITY");
        priority.setFieldType(DATA_TYPE.ListNode.name());
        fields.add(priority);

        return fields;
    }
    
    @Override
    public List<AgileEntityFieldValue> getAgileEntityFieldsValueList(final String agileProjectValue,
            final String entityType,
            final ValueSet instanceConfigurationParameters, final String fieldName, final boolean isLogicalName)
    {

        // The result of this method is used to list values of a list-type field that can be mapped with a PPM drop-down list field in the Configuration UI.

        // The "fieldName" is just a text to specify which list-type field's values user want to get
        // the sample code gives an example of field "priority".

        List<AgileEntityFieldValue> fields = new ArrayList<AgileEntityFieldValue>();
        AgileEntityFieldValue high = new AgileEntityFieldValue();
        high.setId("high");
        high.setName("High");
        fields.add(high);
        
        AgileEntityFieldValue normal = new AgileEntityFieldValue();
        normal.setId("normal");
        normal.setName("Normal");
        fields.add(normal);
        
        AgileEntityFieldValue low = new AgileEntityFieldValue();
        low.setId("low");
        low.setName("Low");
        fields.add(low);

        return fields;
    }

    @Override public AgileEntity updateEntity(String agileProjectValue, String entityType, AgileEntity entity,
            ValueSet instanceConfigurationParameters)
    {
        // There's no real action to do here since the agile entity doesn't exist for real.
        // In a connector, you'd make REST calls to update the fields of the entity in the agile tool.

        // We don't care about fields values when updating an entity, only lastUpdateTime info is important
        // as it's used to sync the field in the right tool if fields have been modified
        // in both PPM and the agile tool.

        return getDummyEntity();
    }

    @Override public AgileEntity createEntity(String agileProjectValue, String entityType, AgileEntity entity,
            ValueSet instanceConfigurationParameters)
    {
        // There's no real action to do here since the agile entity doesn't exist for real.
        // In a connector, you'd make REST calls to create the fields of the entity in the agile tool.

        // We don't care about fields values when creating an entity, only lastUpdateTime info is important
        // as it's used to sync the field in the right tool if fields have been modified
        // in both PPM and the agile tool.

        return getDummyEntity();
    }
    

    @Override public List<AgileEntity> getEntities(String agileProjectValue, String entityType,
            ValueSet instanceConfigurationParameters, Set<String> entityIds, Date lastUpdateTime)
    {
        // This method is called to only retrieve entities from the agile tools that have been modified after a specific time (lastUpdateTime).
        // It is used to optimize performance and not retrieve agile entities that haven't been modified in the agile tool.

        List<AgileEntity> entities = new ArrayList<>();
        entities.add(getDummyEntity());
        return entities;
    }

    @Override public AgileEntity getEntity(String agileProjectValue, String entityType,
            ValueSet instanceConfigurationParameters, String entityId)
    {
        // This method gets called by PPM to retrieve information of a single entity
        // and make sure the lastUpdateDate doesn't conflict with last update date on PPM side.
        return getDummyEntity();
    }

    private AgileEntity getDummyEntity() {
        AgileEntity dummyEntity = new AgileEntity();
        dummyEntity.setEntityUrl("https://github.com/MicroFocus/ppm-agileSDK-home/wiki");
        dummyEntity.setId("12345");
        dummyEntity.setLastUpdateTime(new Date());

        StringField nameValue = new StringField();
        nameValue.set("My entity Name");
        dummyEntity.addField("NAME_FIELD", nameValue);

        StringField storyPointsValue = new StringField();
        storyPointsValue.set("13");
        dummyEntity.addField("STORY_POINTS_FIELD", storyPointsValue);
        
        ListNode listNode = new ListNode();                            
        listNode.setId("low");
        listNode.setName("Low");                            
        
        ListNodeField listNodeField = new ListNodeField();
        listNodeField.set(listNode);        
        dummyEntity.addField("PRIORITY", listNodeField);

        UserField assigneeValue = new UserField();
        // We're setting the User info manually to Admin User since we know it's a PPM user that should exist;
        // In a connector, when you're retrieving user info from the Agile tool, it's your responsibility to match these agile users to some PPM Users.
        // @see {@link com.ppm.integration.agilesdk.provider.UserProvider#getByEmail(String email)};
        com.hp.ppm.user.model.User ppmAdmin = Providers.getUserProvider(SampleIntegrationConnector.class).getByUsername("admin");
        User adminUser = new User();
        adminUser.setUserId(1L);
        adminUser.setUsername("admin");
        adminUser.setFullName("Admin User");
        adminUser.setFullName("admin@ppm-demo.com");

        if (ppmAdmin != null) {
            adminUser.setEmail(ppmAdmin.getEmail());
            adminUser.setFullName(ppmAdmin.getFullName());
        }

        assigneeValue.set(adminUser);
        dummyEntity.addField("ASSIGNEE_FIELD", assigneeValue);

        return dummyEntity;
    }
}
