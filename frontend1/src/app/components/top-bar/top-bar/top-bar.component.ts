import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {

  items: { route: string, icon: string, label: string }[] = [
    {
      route: 'main',
      icon: 'home',
      label: 'home'
    },
    {
      route: 'switch',
      icon: 'cell_wifi',
      label: 'networks'
    },
    {
      route: 'device',
      icon: 'devices',
      label: 'devices'
    },
    {
      route: 'rooms',
      icon: 'room',
      label: 'rooms'
    },
    {
      route: 'building',
      icon: 'business',
      label: 'buildings'
    },
    {
      route: 'settings',
      icon: 'build',
      label: 'settings'
    },
    {
      route: 'login',
      icon: 'exit_to_app',
      label: 'logout'
    }
  ]

  constructor() { }

  ngOnInit() {
  }

}
