import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Building} from '../../../models/building';
import {Floor} from '../../../models/floor';
import {BuildingService} from '../../../services/building.service';
import {Snackbar} from '../../snackbar';

@Component({
  selector: 'app-building-details',
  templateUrl: './building-details.component.html',
  styleUrls: ['./building-details.component.scss']
})
export class BuildingDetailsComponent implements OnInit {
  @Input()
  building: Building;
  editedBuilding: Building = {name: '', floors: []} as Building;

  @Output()
  hide: EventEmitter<void> = new EventEmitter();

  @Output()
  refresh: EventEmitter<void> = new EventEmitter();

  @Output()
  newBuilding: EventEmitter<Building> = new EventEmitter();

  @ViewChild('inputFiles', {static: false})
  inputFiles: ElementRef;

  constructor(private buildingService: BuildingService, private snackBar: Snackbar) {
  }

  ngOnInit() {
    this.editedBuilding = JSON.parse(JSON.stringify(this.building));
    console.log(this);
  }

  async create() {
    await this.buildingService.uploadBuilding(this.editedBuilding);
  }

  async update() {
    const {building, editedBuilding} = this;

    if (building.name !== editedBuilding.name) {
      await this.buildingService.renameBuilding(this.building.name, this.editedBuilding.name);
      building.name = editedBuilding.name;
    }

    for (const oldFloor of building.floors) {
      const floor = editedBuilding.floors.find(fl => fl.image === oldFloor.image);

      if (!floor) {
        await this.buildingService.deleteFloor(building.name, oldFloor.name);
        building.floors.splice(building.floors.indexOf(oldFloor), 1);
      } else if (floor.name !== oldFloor.name) {
        await this.buildingService.renameFloor(building.name, oldFloor.name, floor.name);
        oldFloor.image = oldFloor.image.replace(oldFloor.name, floor.name);
        floor.image = oldFloor.image;
        oldFloor.name = floor.name;
      }
    }

    for (const floor of editedBuilding.floors) {
      const oldFloor = building.floors.find(fl => fl.image === floor.image);

      // if floor is in new floors but not in old floors
      if (!oldFloor) {
        console.log('floor added!', null, floor);
        await this.buildingService.uploadBuildingFloor(building.name, floor);
        building.floors.push(floor);
      }
    }
  }

  async save() {
    try {
      if (this.building.name === '') {
        await this.create();
        this.snackBar.showSnackbar('Created a new building! :)', 'save');
        this.newBuilding.emit(this.editedBuilding);
      } else {
        await this.update();
        this.snackBar.showSnackbar(`Saved building ${this.building.name}!`, 'info');
      }
      this.hide.emit();
    } catch (err) {
      this.snackBar.showSnackbar('Something went wrong :(', 'error');
      console.error(err);
    }
  }

  deleteFloor(floor: Floor) {
    const index = this.editedBuilding.floors.indexOf(floor);
    if (index === -1) {
      return;
    }
    this.editedBuilding.floors.splice(index, 1);
  }

  async handleFileInput($event: Event) {
    const files = ($event.target as HTMLInputElement).files;
    const fileArray = Array.from(files);
    for (const file of fileArray) {
      const url = await this.readFile(file);
      this.editedBuilding.floors.push({
        name: file.name,
        image: url,
        file
      });
    }
  }

  readFile(file: File): Promise<string> {
    return new Promise<string>((resolve) => {
      const reader = new FileReader();
      reader.addEventListener('load', (event: any) => {
        resolve(event.target.result);
      });
      reader.readAsDataURL(file);
    });
  }
}

