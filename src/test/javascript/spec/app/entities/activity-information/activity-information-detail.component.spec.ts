import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { EPaymentTestModule } from '../../../test.module';
import { ActivityInformationDetailComponent } from 'app/entities/activity-information/activity-information-detail.component';
import { ActivityInformation } from 'app/shared/model/activity-information.model';

describe('Component Tests', () => {
  describe('ActivityInformation Management Detail Component', () => {
    let comp: ActivityInformationDetailComponent;
    let fixture: ComponentFixture<ActivityInformationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ activityInformation: new ActivityInformation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EPaymentTestModule],
        declarations: [ActivityInformationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ActivityInformationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityInformationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load activityInformation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityInformation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
