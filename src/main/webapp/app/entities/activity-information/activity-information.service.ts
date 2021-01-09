import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActivityInformation } from 'app/shared/model/activity-information.model';

type EntityResponseType = HttpResponse<IActivityInformation>;
type EntityArrayResponseType = HttpResponse<IActivityInformation[]>;

@Injectable({ providedIn: 'root' })
export class ActivityInformationService {
  public resourceUrl = SERVER_API_URL + 'api/activity-informations';

  constructor(protected http: HttpClient) {}

  create(activityInformation: IActivityInformation): Observable<EntityResponseType> {
    return this.http.post<IActivityInformation>(this.resourceUrl, activityInformation, { observe: 'response' });
  }

  update(activityInformation: IActivityInformation): Observable<EntityResponseType> {
    return this.http.put<IActivityInformation>(this.resourceUrl, activityInformation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
