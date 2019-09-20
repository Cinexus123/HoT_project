import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Building } from '../models/building'
import { Floor } from '../models/floor'
import { environment } from '../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class BuildingService {
  private url = `${environment.api.url}/buildings`
  //private url = 'http://hotdev.ncdc:8280/api/buildings'

  constructor (private http: HttpClient) {
  }

  getAllBuildings () {
    return this.http.get<Building[]>(this.url).toPromise()
  }

  deleteBuilding (name: string) {
    return this.http.delete(`${this.url}/${name}`).toPromise()
  }

  uploadBuilding (building: Building) {
    const formData = new FormData()
    formData.append('buildingName', building.name)
    for (const floor of building.floors) {
      formData.append('files', floor.file, floor.file.name)
    }

    return this.http.post<any>(this.url, formData).toPromise()
  }

  renameFloor (buildingName: string, floorName: string, newFloorName: string) {
    return this.http.patch(`${this.url}/${buildingName}/${floorName}`, { name: newFloorName }).toPromise()
  }

  deleteFloor (buildingName: string, floorName: string) {
    return this.http.delete(`${this.url}/${buildingName}/${floorName}`).toPromise()
  }

  renameBuilding (buildingName: string, newBuildingName: string) {
    return this.http.patch(`${this.url}/${buildingName}`, { name: newBuildingName }).toPromise()
  }

  uploadBuildingFloor (buildingName: string, floor: Floor) {
    const formData = new FormData()
    formData.append('buildingName', buildingName)
    formData.append('file', floor.file, floor.name)
    return this.http.post(`${this.url}/${buildingName}`, formData).toPromise()
  }
}
