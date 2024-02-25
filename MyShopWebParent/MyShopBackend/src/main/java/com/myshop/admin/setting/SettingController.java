package com.myshop.admin.setting;

import com.myshop.admin.FileUploadUtils;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.Constants;
import com.myshop.common.entity.Setting;
import com.myshop.common.entity.SettingBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/settings")
    public String listSettings(Model model) {
        List<Setting> settingList = settingService.listAll();
        for (var setting : settingList) {
            model.addAttribute(setting.getKey(), setting.getValue());
            model.addAttribute("AWS_BASE_URI", Constants.AWS_BASE_URI);
            model.addAttribute("pageTitle", "Settings");
        }
        return "settings/settings";
    }

    @PostMapping("/settings/save_general")
    public String updateSettings(@RequestParam("fileImage") MultipartFile multipartFile,
                                 HttpServletRequest servletRequest,
                                 RedirectAttributes redirectAttributes) throws IOException {
        List<Setting> generalSettings = settingService.listGeneralSetting();
        SettingBag settingBag = new SettingBag(generalSettings);

        saveSiteLogo(multipartFile,settingBag);
        saveInfoInForm(servletRequest,settingBag,generalSettings);
        redirectAttributes.addFlashAttribute("message_success", "The settings has been saved successfully!");
        return "redirect:/settings";
    }

    private void saveInfoInForm(HttpServletRequest servletRequest, SettingBag settingBag, List<Setting> settings) {
        for(var setting : settings) {
            String value = servletRequest.getParameter(setting.getKey());
            if(value!=null) {
                settingBag.updateValue(setting.getKey(),value);
            }
        }
        settingService.updateSetting(settings);
    }

    private void saveSiteLogo(MultipartFile multipartFile, SettingBag settingBag) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String valuePath = "/site-logo/" + fileName;
            settingBag.updateValue("SITE_LOGO", valuePath);
            String uploadDir = "site-logo";
//            //  no upload to local device instead of upload to amazon s3
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

        }
    }

    @PostMapping("/settings/save_mail_server")
    public String saveMailServer(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        List<Setting> settingList = settingService.listMailServerSetting();
        SettingBag settingBag = new SettingBag(settingList);
        saveInfoInForm(request,settingBag,settingList);
        redirectAttributes.addFlashAttribute("message_success","The setting has been saved successfully!");
        return "redirect:/settings";
    }

    @PostMapping("/settings/save_mail_template")
    public String saveMailTemplate(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        List<Setting> settingList = settingService.listMailTemplateSetting();
        SettingBag settingBag = new SettingBag(settingList);
        saveInfoInForm(request,settingBag,settingList);
        redirectAttributes.addFlashAttribute("message_success","The setting has been saved successfully!");
        return "redirect:/settings";
    }
}
