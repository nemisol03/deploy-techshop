package com.myshop.admin.setting;

import com.myshop.common.entity.Setting;
import com.myshop.common.entity.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepository;

    public List<Setting> listAll() {
        return (List<Setting>) settingRepository.findAll();
    }

    public List<Setting> listGeneralSetting() {
        return settingRepository.findByCategory(SettingCategory.GENERAL);
    }
    public List<Setting> listMailServerSetting() {
        return settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
    }
    public List<Setting> listMailTemplateSetting() {
        return settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATE);
    }

    public void updateSetting(List<Setting> settings) {
        settingRepository.saveAll(settings);
    }
}
