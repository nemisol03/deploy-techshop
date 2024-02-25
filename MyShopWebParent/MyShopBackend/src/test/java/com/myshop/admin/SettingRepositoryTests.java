package com.myshop.admin;

import com.myshop.admin.setting.SettingRepository;
import com.myshop.common.entity.Setting;
import com.myshop.common.entity.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {
    @Autowired
    private SettingRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateSettingItem() {
        Setting siteName = new Setting("SITE_NAME","myShop");
        Setting siteLogo = new Setting("SITE_LOGO","logo.jpg");
        Setting copyright = new Setting("COPYRIGHT","This is a my copyright web");
        Iterable<Setting> iterable = repo.saveAll(List.of(siteName, siteLogo, copyright));
        iterable.forEach(System.out::println);
    }

    @Test
    public void testUpdateSettings() {
        Setting siteName = entityManager.find(Setting.class,1);
        Setting siteLogo = entityManager.find(Setting.class,2);
        Setting copyright = entityManager.find(Setting.class,3);
        siteName.setCategory(SettingCategory.GENERAL);
        siteLogo.setCategory(SettingCategory.GENERAL);
        copyright.setCategory(SettingCategory.GENERAL);
        Iterable<Setting> iterable = repo.saveAll(List.of(siteName, siteLogo, copyright));
    }
}
