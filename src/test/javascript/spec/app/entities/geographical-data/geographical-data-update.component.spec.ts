import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EPaymentTestModule } from '../../../test.module';
import { GeographicalDataUpdateComponent } from 'app/entities/geographical-data/geographical-data-update.component';
import { GeographicalDataService } from 'app/entities/geographical-data/geographical-data.service';
import { GeographicalData } from 'app/shared/model/geographical-data.model';

describe('Component Tests', () => {
  describe('GeographicalData Management Update Component', () => {
    let comp: GeographicalDataUpdateComponent;
    let fixture: ComponentFixture<GeographicalDataUpdateComponent>;
    let service: GeographicalDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EPaymentTestModule],
        declarations: [GeographicalDataUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GeographicalDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeographicalDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeographicalDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GeographicalData(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GeographicalData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
