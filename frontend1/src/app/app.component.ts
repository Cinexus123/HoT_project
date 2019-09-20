import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Admin-Panel';
  show = false;

  constructor(private router: Router) {
    router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe((e: NavigationEnd) => {
        console.log(e.url);
        switch (e.url) {
          case '/main':
            this.show = false;
            break;
          case '/login':
            this.show = false;
            break;
          case '/':
            this.show = false;
            break;
          default:
            this.show = true;
            break;
        }
      });
  }

}

