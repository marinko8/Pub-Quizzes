import { TestBed } from '@angular/core/testing';

import { KvizoviService } from './kvizovi.service';

describe('KvizoviService', () => {
  let service: KvizoviService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KvizoviService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
