package com.myshop.site.setting;

import com.myshop.common.entity.Setting;
import com.myshop.common.entity.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired private SettingRepository settingRepository;

    public List<Setting> listGeneralSetting() {
        return (List<Setting>) settingRepository.findAll();
    }

    public EmailSettingBag listEmailSettings() {
        List<Setting> emailSettings = settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATE);
        emailSettings.addAll(settingRepository.findByCategory(SettingCategory.MAIL_SERVER));
        return new EmailSettingBag(emailSettings);
    }
}
