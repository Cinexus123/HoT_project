import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Device} from '../models/device';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private url = `${environment.api.url}/devices`
  constructor (private http: HttpClient) {
  }

  getAllDevices () {
    return this.http.get<Device[]>(this.url).toPromise();
  }

   save (device: Device) {
    return this.http.post<Device>(this.url, device).toPromise();
  }

  delete (device: Device) {
    return this.http.delete(`${this.url}/${device.id}`).toPromise();
  }
}

