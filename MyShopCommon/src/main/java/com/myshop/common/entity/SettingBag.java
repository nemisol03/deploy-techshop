package com.myshop.common.entity;

import java.util.List;

public class SettingBag {
    private List<Setting> settingList;

    public SettingBag(List<Setting> settingList) {
        this.settingList = settingList;
    }

    public Setting get(String key) {
        int index = settingList.indexOf(new Setting(key));
        if(index >=0) {
            return settingList.get(index);
        }
        return null;
    }

    public String getValue(String key) {
        Setting setting = get(key);
        if(setting!=null) {
            return setting.getValue();
        }
        return null;
    }

    public void updateValue(String key,String value) {
        Setting setting = get(key);
        if(setting!=null) {
            setting.setValue(value);
        }
    }

    public List<Setting> list() {
        return settingList;
    }
}
