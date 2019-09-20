import {Component, OnInit} from '@angular/core';
import {BuildingService} from '../../services/building.service';
import {Building} from '../../models/building';
import {MatSnackBar} from '@angular/material';
import {Snackbar} from '../snackbar';

@Component({
  selector: 'app-building',
  templateUrl: './building.component.html',
  styleUrls: ['./building.component.scss']
})
export class BuildingComponent implements OnInit {
  selectedBuilding: Building;
  buildings: Building[] = [];

  constructor(private buildingService: BuildingService,
              private snackBar: MatSnackBar, private snack: Snackbar) {
  }

  async getAllBuildings() {
    this.buildings = await this.buildingService.getAllBuildings();
  }

  ngOnInit() {
    this.getAllBuildings();
  }

  addBuilding() {
    this.selectedBuilding = {name: '', floors: []} as Building;
  }

  addNewBuilding(building: Building) {
    if (!this.buildings) {
      this.buildings = [];
    }
    this.buildings.push(building);
  }

  select(building: Building) {
    this.selectedBuilding = building;
  }

  async delete(building: Building) {
    await this.buildingService.deleteBuilding(building.name);
    this.selectedBuilding = null;
    this.snack.showSnackbar('Deleted success', 'info');
    const index = this.buildings.indexOf(building);
    if (index === -1) {
      return;
    }
    this.buildings.splice(index, 1);
  }

  refresh() {
    return this.getAllBuildings();
  }

}
