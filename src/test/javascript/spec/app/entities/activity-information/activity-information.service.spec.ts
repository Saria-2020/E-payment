import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivityInformationService } from 'app/entities/activity-information/activity-information.service';
import { IActivityInformation, ActivityInformation } from 'app/shared/model/activity-information.model';

describe('Service Tests', () => {
  describe('ActivityInformation Service', () => {
    let injector: TestBed;
    let service: ActivityInformationService;
    let httpMock: HttpTestingController;
    let elemDefault: IActivityInformation;
    let expectedResult: IActivityInformation | IActivityInformation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ActivityInformationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ActivityInformation(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActivityInformation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ActivityInformation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActivityInformation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sector: 'BBBBBB',
            typeOfActivity: 'BBBBBB',
            propertyType: 'BBBBBB',
            areaClass: 'BBBBBB',
            architectureType: 'BBBBBB',
            numberOfFloors: 'BBBBBB',
            features: 'BBBBBB',
            descriptionOfTheFeatures: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActivityInformation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sector: 'BBBBBB',
            typeOfActivity: 'BBBBBB',
            propertyType: 'BBBBBB',
            areaClass: 'BBBBBB',
            architectureType: 'BBBBBB',
            numberOfFloors: 'BBBBBB',
            features: 'BBBBBB',
            descriptionOfTheFeatures: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ActivityInformation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
