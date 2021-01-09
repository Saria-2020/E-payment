import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GeographicalDataService } from 'app/entities/geographical-data/geographical-data.service';
import { IGeographicalData, GeographicalData } from 'app/shared/model/geographical-data.model';

describe('Service Tests', () => {
  describe('GeographicalData Service', () => {
    let injector: TestBed;
    let service: GeographicalDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IGeographicalData;
    let expectedResult: IGeographicalData | IGeographicalData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GeographicalDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GeographicalData(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GeographicalData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GeographicalData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GeographicalData', () => {
        const returnedFromService = Object.assign(
          {
            state: 'BBBBBB',
            units: 'BBBBBB',
            district: 'BBBBBB',
            square: 'BBBBBB',
            realEstateNumber: 'BBBBBB',
            activityNumber: 'BBBBBB',
            areaOfTheRealEstate: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GeographicalData', () => {
        const returnedFromService = Object.assign(
          {
            state: 'BBBBBB',
            units: 'BBBBBB',
            district: 'BBBBBB',
            square: 'BBBBBB',
            realEstateNumber: 'BBBBBB',
            activityNumber: 'BBBBBB',
            areaOfTheRealEstate: 'BBBBBB',
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

      it('should delete a GeographicalData', () => {
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
