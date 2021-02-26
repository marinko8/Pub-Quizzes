import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewKvizComponent } from './view-kviz.component';

describe('ViewKvizComponent', () => {
  let component: ViewKvizComponent;
  let fixture: ComponentFixture<ViewKvizComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewKvizComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewKvizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
