package com.api.services.impl;

import com.api.commands.SettingsCommand;
import com.api.entities.Settings;
import com.api.repositories.SettingRepository;
import com.api.services.SettingsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Value("${logging.file}")
    String logesFilePath;

    private SettingRepository settingRepository;

    public SettingsServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public void setSettings(SettingsCommand settingsCommand) {
        Settings settings = settingRepository.findById(1L).isPresent()
            ? settingRepository.findById(1L).get()
            : new Settings(settingsCommand.getMap(),
            settingsCommand.getUserPhoto(),
            settingsCommand.getDescTxt(),
            settingsCommand.getDescMap(),
            settingsCommand.getGoogleMeetings(),
            settingsCommand.getDate(),
            settingsCommand.getBusiness());

        if (settings.getId() != null) {
            settings.setMap(settingsCommand.getMap());
            settings.setUserPhoto(settingsCommand.getUserPhoto());
            settings.setDescTxt(settingsCommand.getDescTxt());
            settings.setDescMap(settingsCommand.getDescMap());
            settings.setBusiness(settingsCommand.getBusiness());
            settings.setGoogleMeetings(settingsCommand.getGoogleMeetings());
            settings.setDate(settingsCommand.getDate());

        }
        settingRepository.save(settings);
    }

    @Override
    public Settings getSettings() {
        return settingRepository.findById(1L).isPresent() ? settingRepository.findById(1L).get() : null;
    }

    public List<String> getLogs() {
        String path = Paths.get(logesFilePath).toAbsolutePath().normalize().toString();
        File file = new File(path);
        InputStream inputStream;
        try {
            inputStream = new DataInputStream(new FileInputStream(file));
            String[] x = StreamUtils.copyToString(inputStream, Charset.defaultCharset()).split("\n");
            List<String> list = Arrays.asList(x);
            return list.subList(list.size() - 100, list.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
