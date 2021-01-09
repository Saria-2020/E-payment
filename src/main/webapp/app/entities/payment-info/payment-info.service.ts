import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentInfo } from 'app/shared/model/payment-info.model';

type EntityResponseType = HttpResponse<IPaymentInfo>;
type EntityArrayResponseType = HttpResponse<IPaymentInfo[]>;

@Injectable({ providedIn: 'root' })
export class PaymentInfoService {
  public resourceUrl = SERVER_API_URL + 'api/payment-infos';

  constructor(protected http: HttpClient) {}

  create(paymentInfo: IPaymentInfo): Observable<EntityResponseType> {
    return this.http.post<IPaymentInfo>(this.resourceUrl, paymentInfo, { observe: 'response' });
  }

  update(paymentInfo: IPaymentInfo): Observable<EntityResponseType> {
    return this.http.put<IPaymentInfo>(this.resourceUrl, paymentInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
