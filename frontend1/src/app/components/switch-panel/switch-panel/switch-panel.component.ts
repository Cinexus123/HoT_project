import {Component, OnInit, ViewChild} from '@angular/core';
import {Switch} from '../../../models/switch';
import {FormControl} from '@angular/forms';
import {MatTableDataSource} from '@angular/material';
import {SwitchService} from '../../../services/switch.service';
import {Building} from '../../../models/building';
import {BuildingService} from '../../../services/building.service';
import {Floor} from '../../../models/floor';
import {Snackbar} from '../../snackbar';

@Component({
  selector: 'app-switch-panel',
  templateUrl: './switch-panel.component.html',
  styleUrls: ['./switch-panel.component.scss']
})
export class SwitchPanelComponent implements OnInit {

  constructor(private switchService: SwitchService, private buildingService: BuildingService, private snackBar: Snackbar) {
  }

  @ViewChild('marker', {static: false}) marker: { nativeElement: HTMLImageElement };
  switches: Switch[] = [];
  buildings: Building[] = [];
  floors: Floor[] = [];
  selectedFloor: Floor;
  selectedBuilding: Building;
  selectedSwitch: Switch = null;
  editedSwitch: Switch = null;
  myControl = new FormControl();
  displayedColumns: string[] = ['id', 'name', 'building', 'floor', 'actions'];
  dataSource = new MatTableDataSource(this.switches);

  async getAllSwitches() {
    this.dataSource.data = await this.switchService.getAllSwitches();
    console.log(this.dataSource.data);
  }

  async getAllBuildings() {
    this.buildings = await this.buildingService.getAllBuildings();
  }

  selectFloor(floor: Floor) {

    this.selectedFloor = floor;
    this.editedSwitch.floor = floor.name;
    // console.log('aaaaa ' + this.buildings[0].floors);
  }

  selectBuilding(building: Building) {
    this.selectedBuilding = building;
    this.editedSwitch.building = building.name;
    this.floors = building.floors;
  }

  ngOnInit() {
    this.getAllSwitches();
    this.getAllBuildings();
  }

  img(event: MouseEvent) {
    console.log(event);
    const el = (event.target as HTMLImageElement);
    // const rect = el.getBoundingClientRect();
    const scaling = el.naturalWidth / el.clientWidth;
    // const x = Math.floor((event.clientX - rect.left) * scaling);
    // const y = Math.floor((event.clientY - rect.top) * scaling);
    const x = Math.floor(event.layerX * scaling);
    const y = Math.floor(event.layerY * scaling);

    const coords = 'X coords: ' + x + ', Y coords: ' + y + ', scaling: ' + scaling;
    console.log('coords= ' + coords);

    this.marker.nativeElement.hidden = false;
    console.log(event);
    this.marker.nativeElement.style.left = event.offsetX - 18 + 'px';
    this.marker.nativeElement.style.top = event.offsetY - 35 + 'px';
    this.editedSwitch.x = x;
    this.editedSwitch.y = y;
  }

  select(switchh: Switch) {
    console.log(switchh);
    this.selectedSwitch = switchh;
    this.editedSwitch = JSON.parse(JSON.stringify(switchh));
    console.log(this.floors); // klonowanie oryginalnego device
  }

  async save() {
    for (const name of ['building', 'floor', 'description', 'x', 'y']) {
      this.selectedSwitch[name] = this.editedSwitch[name];
    }
    if (this.selectedSwitch.name == null) {
      this.selectedSwitch.name = this.editedSwitch.name;
    }
    console.log('editedSwitch');
    console.log(this.editedSwitch);
    console.log('selectedSwitch');
    console.log(this.selectedSwitch);

    console.log(this.selectedSwitch);
    if (this.selectedSwitch.id) {
      await this.switchService.update(this.selectedSwitch)
        .then(() => this.snackBar.showSnackbar('Save succeeded', 'info'))
        .catch(() => this.snackBar.showSnackbar('Save refused', 'error'));
    } else {
      await this.switchService.save(this.selectedSwitch)
        .then(() => this.snackBar.showSnackbar('Save succeeded', 'info'))
        .catch(() => this.snackBar.showSnackbar('Save refused', 'error'));
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addNewNetwork() {
    const newSwitch: Switch = {
      id: null,
      name: null,
      description: null,
      x: null,
      y: null,
      building: null,
      floor: null
    };

    this.select(newSwitch);
  }


  async delete(switc: Switch) {
    await this.switchService.delete(switc)
      .then(() => {
        this.snackBar.showSnackbar('Deletion succeeded', 'info');
        this.getAllSwitches();
      })
      .catch(() => this.snackBar.showSnackbar('Deletion refused', 'error'));
  }
}
