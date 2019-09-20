package com.api.services;

import com.api.commands.SettingsCommand;
import com.api.entities.Settings;

public interface SettingsService {
    void setSettings(SettingsCommand settingsCommand);

    Settings getSettings();
}
