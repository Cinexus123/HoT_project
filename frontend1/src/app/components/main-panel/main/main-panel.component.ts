import { Component } from '@angular/core'

@Component({
  selector: 'app-main-panel',
  templateUrl: './main-panel.component.html',
  styleUrls: [ './main-panel.component.scss' ]
})
export class MainPanelComponent {

  items = [
    {
      route: '/device',
      name: 'DEVICES',
      background: 'url(assets/img/planets/mars.jpg)',
      shadow: '0 0 150px -20px rgba(255, 132, 0, 0.54), -14px -15px 40px -10px rgba(255, 238, 191, 0.23)'
    },
    {
      route: '/switch',
      name: 'NETWORKS',
      background: 'url(assets/img/planets/earth.jpg)',
      shadow: '0 0 150px -20px rgba(13, 0, 255, 0.3), -14px -15px 40px -10px rgba(255, 238, 191, 0.23)'
    },
    {
      route: '/building',
      name: 'BUILDINGS',
      background: 'url(assets/img/planets/venus.jpg)',
      shadow: '0 0 150px -20px rgba(255, 189, 3, 0.52), -14px -15px 40px -10px rgba(255, 238, 191, 0.23)'
    },
    {
      route: '/rooms',
      name: 'ROOMS',
      background: 'url(assets/img/planets/saturn.jpg)',
      shadow: '0 0 150px -20px rgba(198, 113, 225, 0.54), -14px -15px 40px -10px rgba(255, 238, 191, 0.23)'
    }, {
      route: '/settings',
      name: 'SETTINGS',
      background: 'url(assets/img/planets/mercury.png)',
      shadow: ' 0 0 100px -20px rgba(255, 255, 255, 0.52), -14px -15px 40px -10px rgba(255, 238, 191, 0.23)'
    }
  ]

  constructor () {
  }
}
