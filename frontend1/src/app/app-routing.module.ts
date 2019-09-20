import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginPanelComponent} from './components/login-panel/login-panel.component';
import {MainPanelComponent} from './components/main-panel/main/main-panel.component';
import {DeviceListComponent} from './components/devices/device-list/device-list.component';
import {BuildingComponent} from './components/building/building.component';
import {SwitchPanelComponent} from './components/switch-panel/switch-panel/switch-panel.component';
import {SettingsPanelComponent} from './components/settings-panel/settings-panel.component';
import {RoomsComponent} from './components/rooms/rooms.component';



const routes: Routes = [
  {
    path: 'main',
    component: MainPanelComponent
  },
  {
    path: 'building',
    component: BuildingComponent
  },
  {
    path: 'login',
    component: LoginPanelComponent
  },
  {
    path: 'device',
    component: DeviceListComponent
  },
  {
    path: 'switch',
    component: SwitchPanelComponent
  },
  {
   path: 'settings',
   component: SettingsPanelComponent
  },
  {
    path: 'rooms',
    component: RoomsComponent
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
