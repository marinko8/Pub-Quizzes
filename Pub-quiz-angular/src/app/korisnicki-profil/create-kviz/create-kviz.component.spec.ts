import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateKvizComponent } from './create-kviz.component';

describe('CreateKvizComponent', () => {
  let component: CreateKvizComponent;
  let fixture: ComponentFixture<CreateKvizComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateKvizComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateKvizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
