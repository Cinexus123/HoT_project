import {Component, OnInit, ViewChild} from '@angular/core';
import {Building} from '../../models/building';
import {BuildingService} from '../../services/building.service';
import {Floor} from '../../models/floor';
import {RoomService} from '../../services/room.service';
import {Room} from '../../models/room';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-floors',
  templateUrl: './rooms.component.html',
  styleUrls: [ './rooms.component.scss' ]
})
export class RoomsComponent implements OnInit {

  @ViewChild('marker', { static: false }) marker: { nativeElement: HTMLImageElement }
  buildings: Building[] = []
  private selectedBuilding: Building
  floors: Floor[] = []
  selectedFloor: Floor
  selectedRoom: Room
  editedRoom: Room
  rooms: Room[] = []
  displayedColumns: string[] = [ 'id', 'name', 'building', 'floor', 'actions' ]
  dataSource = new MatTableDataSource(this.rooms)

  constructor (private roomService: RoomService, private buildingService: BuildingService) {
  }

  async getAllRooms () {
    this.dataSource.data = await this.roomService.getAllRooms()
  }
  async getAllBuildings () {
    this.buildings = await this.buildingService.getAllBuildings()
  }

  ngOnInit () {
    this.getAllRooms()
    this.getAllBuildings()
  }

  img (event: MouseEvent) {
    console.log(event)
    const el = (event.target as HTMLImageElement)
    const rect = el.getBoundingClientRect()
    const scaling = el.naturalWidth / el.clientWidth
    const x = Math.floor((event.clientX - rect.left) * scaling)
    const y = Math.floor((event.clientY - rect.top) * scaling)
    const coords = 'X coords: ' + x + ', Y coords: ' + y + ', scaling: ' + scaling
    console.log('coords= ' + coords)

    this.marker.nativeElement.hidden = false
    console.log(event)
    this.marker.nativeElement.style.left = event.offsetX - 18 + 'px'
    this.marker.nativeElement.style.top = event.offsetY - 35 + 'px'
    this.editedRoom.xposition = x
    this.editedRoom.yposition = y
  }

  selectFloor (floor: Floor) {

    this.selectedFloor = floor
  }

  selectRoom (room: Room) {

    this.selectedRoom = room
    this.editedRoom = JSON.parse(JSON.stringify(room))
  }

  selectBuilding (building: Building) {
    this.selectedBuilding = building
    this.floors = building.floors
  }

  async save () {
    for (const name of [ 'building', 'floor', 'name', 'xposition', 'yposition' ]) {
      this.selectedRoom[name] = this.editedRoom[name]
    }
    await this.roomService.save(this.selectedRoom)
    this.dataSource.data.push(this.selectedRoom)
    this.getAllRooms()
  }

  applyFilter (filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase()
  }

  addRoom () {
    this.selectedRoom = { name: ''} as Room
    this.editedRoom = JSON.parse(JSON.stringify({ name: ''} as Room))
  }

  async delete (room: Room) {
    await this.roomService.delete(room)
    this.selectedRoom = null
    this.getAllRooms();
  }
}
