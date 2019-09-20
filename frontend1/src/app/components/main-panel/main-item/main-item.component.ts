import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-main-item',
  templateUrl: './main-item.component.html',
  styleUrls: ['./main-item.component.scss']
})
export class MainItemComponent {

  @Input()
  item: {
    route: string,
    name: string,
    background: string,
    shadow: string
  };

  constructor() {
  }
}
