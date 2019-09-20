import {Component, Input, OnInit} from '@angular/core';
import {DeviceService} from '../../../services/device.service';
import {Device} from '../../../models/device';
import {MatTableDataSource} from '@angular/material';
@Component({
  selector: 'app-device-list',
  templateUrl: './device-list.component.html',
  styleUrls: ['./device-list.component.scss']
})
export class DeviceListComponent implements OnInit {

  constructor (private deviceService: DeviceService) {
  }
  devices: Device[] = [];

  displayedColumns: string[] = ['id', 'nickName', 'macAddress', 'deviceName', 'actions'];
  dataSource = new MatTableDataSource(this.devices);

  @Input()
  selectedDevice: Device;

  editedDevice: Device = null;

  async getAllDevices () {
    this.dataSource.data = await this.deviceService.getAllDevices();
   // console.log(this.dataSource.data);
  }

  ngOnInit () {
    this.getAllDevices();
  }

  applyFilter (filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  async delete (device: Device) {

    await this.deviceService.delete(device);
    this.getAllDevices()
  }

  select (device: Device) {
    //console.log(device);
    this.selectedDevice = device;
    this.editedDevice = JSON.parse(JSON.stringify(device));

    // klonowanie oryginalnego device
  }

  async save () {

    for (const name of ['nickName', 'macAddress', 'deviceName']) {
      this.selectedDevice[name] = this.editedDevice[name];
    }

    await this.deviceService.save(this.selectedDevice);

  }
}



