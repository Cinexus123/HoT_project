import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoginPanelComponent} from './components/login-panel/login-panel.component';
import {MainPanelComponent} from './components/main-panel/main/main-panel.component';
import {TopBarComponent} from './components/top-bar/top-bar/top-bar.component';
import {DeviceListComponent} from './components/devices/device-list/device-list.component';
import {BuildingComponent} from './components/building/building.component';
import {AuthInterceptor} from './http-interceptors/auth-interceptor';
import {BuildingDetailsComponent} from './components/building/building-details/building-details.component';
import {OverlayModule} from '@angular/cdk/overlay';
import {SwitchPanelComponent} from './components/switch-panel/switch-panel/switch-panel.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule, MatCheckboxModule,
  MatFormFieldModule, MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule, MatMenuModule,
  MatOptionModule, MatRadioModule,
  MatRippleModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatStepperModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import { SettingsPanelComponent } from './components/settings-panel/settings-panel.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { TopBarItemComponent } from './components/top-bar-item/top-bar-item.component';
import { MainItemComponent } from './components/main-panel/main-item/main-item.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginPanelComponent,
    MainPanelComponent,
    TopBarComponent,
    DeviceListComponent,
    BuildingComponent,
    BuildingDetailsComponent,
    SwitchPanelComponent,
    BuildingDetailsComponent,
    SwitchPanelComponent,
    BuildingDetailsComponent,
    SwitchPanelComponent,
    BuildingDetailsComponent,
    SwitchPanelComponent,
    SettingsPanelComponent,
    RoomsComponent,
    TopBarItemComponent,
    MainItemComponent
  ],
  imports: [
    OverlayModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatCardModule,
    FormsModule,
    BrowserAnimationsModule,
    MatListModule,
    MatSidenavModule,
    MatRippleModule,
    MatToolbarModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatOptionModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatTableModule,
    MatStepperModule,
    MatGridListModule,
    MatCheckboxModule,
    MatRadioModule,
    MatMenuModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
