import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KorisnickiProfilComponent } from './korisnicki-profil.component';

describe('KorisnickiProfilComponent', () => {
  let component: KorisnickiProfilComponent;
  let fixture: ComponentFixture<KorisnickiProfilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KorisnickiProfilComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KorisnickiProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
