import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Settings} from '../models/settings';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private url = `${environment.api.url}/settings`;

  constructor(private  http: HttpClient) {
  }

  async getAll() {
    return this.http.get<Settings>(this.url).toPromise();
  }

  async save(settings: Settings) {
    return this.http.post<Settings>(this.url, settings).toPromise();
  }

  async getLogs() {
    return this.http.get<string[]>(this.url + '/logs').toPromise();
  }
}
