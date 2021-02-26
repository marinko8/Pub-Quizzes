import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KvizoviComponent } from './kvizovi.component';

describe('KvizoviComponent', () => {
  let component: KvizoviComponent;
  let fixture: ComponentFixture<KvizoviComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KvizoviComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KvizoviComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
