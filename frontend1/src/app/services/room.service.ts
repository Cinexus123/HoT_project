import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Room} from '../models/room';
import {environment} from '../../environments/environment';



@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private url = `${environment.api.url}/room`

  constructor (private http: HttpClient) { }

  save (room: Room) {
    return this.http.post<Room>(`${this.url}/`, room).toPromise();
  }

  getAllRooms () {

    return this.http.get<Room[]>(this.url).toPromise()
  }

  delete(room: Room)
  {
    return this.http.delete(`${this.url}/${room.id}`).toPromise()
  }
}
