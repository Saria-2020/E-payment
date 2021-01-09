import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGeographicalData } from 'app/shared/model/geographical-data.model';

type EntityResponseType = HttpResponse<IGeographicalData>;
type EntityArrayResponseType = HttpResponse<IGeographicalData[]>;

@Injectable({ providedIn: 'root' })
export class GeographicalDataService {
  public resourceUrl = SERVER_API_URL + 'api/geographical-data';

  constructor(protected http: HttpClient) {}

  create(geographicalData: IGeographicalData): Observable<EntityResponseType> {
    return this.http.post<IGeographicalData>(this.resourceUrl, geographicalData, { observe: 'response' });
  }

  update(geographicalData: IGeographicalData): Observable<EntityResponseType> {
    return this.http.put<IGeographicalData>(this.resourceUrl, geographicalData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGeographicalData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeographicalData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
