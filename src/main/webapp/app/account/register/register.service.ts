import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IUser } from 'app/core/user/user.model';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  constructor(private http: HttpClient) {}

  save(account: IUser): Observable<HttpResponse<IUser>> {
    return this.http.post<IUser>(SERVER_API_URL + 'api/register', account, { observe: 'response' });
  }
}
