import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Switch } from '../models/switch'
import { environment } from '../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class SwitchService {

  private url = `${environment.api.url}/networks`

  constructor (private http: HttpClient) {
  }

  getAllSwitches () {
    return this.http.get<Switch[]>(this.url).toPromise()
  }

  save (switch1: Switch) {
    return this.http.post<Switch>(`${this.url}`, switch1).toPromise()
  }

  delete (selectedSwitch: Switch) {
    return this.http.delete(`${this.url}/${selectedSwitch.id}`).toPromise()
  }

  update (selectedSwitch: Switch) {
    return this.http.put<Switch>(`${this.url}/${selectedSwitch.id}`, selectedSwitch).toPromise()
  }
}
