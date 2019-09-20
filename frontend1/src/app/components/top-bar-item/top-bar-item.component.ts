import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-top-bar-item',
  templateUrl: './top-bar-item.component.html',
  styleUrls: ['./top-bar-item.component.scss']
})
export class TopBarItemComponent {

  @Input()
  route: string;

  @Input()
  icon: string;

  @Input()
  label: string;

  constructor () { }
}
