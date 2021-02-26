import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RezultatiDialogComponent } from './rezultati-dialog.component';

describe('RezultatiDialogComponent', () => {
  let component: RezultatiDialogComponent;
  let fixture: ComponentFixture<RezultatiDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RezultatiDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RezultatiDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
