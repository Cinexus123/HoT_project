import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitchPanelComponent } from './switch-panel.component';

describe('SwitchPanelComponent', () => {
  let component: SwitchPanelComponent;
  let fixture: ComponentFixture<SwitchPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwitchPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwitchPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
