import {Component, OnInit} from '@angular/core';
import {SettingsService} from '../../services/settings.service';
import {Settings} from '../../models/settings';
import {Snackbar} from '../snackbar';
import {connectableObservableDescriptor} from 'rxjs/internal/observable/ConnectableObservable';

@Component({
  selector: 'app-settings-panel',
  templateUrl: './settings-panel.component.html',
  styleUrls: ['./settings-panel.component.scss']
})
export class SettingsPanelComponent implements OnInit {
  settings: Settings;
  logs: string[] = [];

  constructor (private settingsService: SettingsService, private snackbar: Snackbar) {
  }

  ngOnInit () {
    this.getSettings();
    this.getLogs();
  }

  async getSettings () {
    this.settings = await this.settingsService.getAll();
  }

  saveSettings () {
    this.settingsService.save(this.settings)
      .then(() => this.snackbar.showSnackbar('Succes save', 'save'))
      .catch(() => this.snackbar.showSnackbar('Error save', 'error'));
    console.log(this.settings);
  }


  async getLogs () {
    this.logs = await this.settingsService.getLogs();
    console.log(this.logs);
  }
}
