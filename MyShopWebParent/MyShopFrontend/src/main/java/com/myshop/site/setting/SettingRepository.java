package com.myshop.site.setting;

import com.myshop.common.entity.Setting;
import com.myshop.common.entity.SettingCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingRepository extends CrudRepository<Setting,Integer> {
    List<Setting> findByCategory(SettingCategory category);
}
