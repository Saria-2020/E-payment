import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EPaymentTestModule } from '../../../test.module';
import { GeographicalDataDetailComponent } from 'app/entities/geographical-data/geographical-data-detail.component';
import { GeographicalData } from 'app/shared/model/geographical-data.model';

describe('Component Tests', () => {
  describe('GeographicalData Management Detail Component', () => {
    let comp: GeographicalDataDetailComponent;
    let fixture: ComponentFixture<GeographicalDataDetailComponent>;
    const route = ({ data: of({ geographicalData: new GeographicalData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EPaymentTestModule],
        declarations: [GeographicalDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GeographicalDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeographicalDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load geographicalData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.geographicalData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
