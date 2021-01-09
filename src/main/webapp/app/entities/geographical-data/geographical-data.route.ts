import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGeographicalData, GeographicalData } from 'app/shared/model/geographical-data.model';
import { GeographicalDataService } from './geographical-data.service';
import { GeographicalDataComponent } from './geographical-data.component';
import { GeographicalDataDetailComponent } from './geographical-data-detail.component';
import { GeographicalDataUpdateComponent } from './geographical-data-update.component';

@Injectable({ providedIn: 'root' })
export class GeographicalDataResolve implements Resolve<IGeographicalData> {
  constructor(private service: GeographicalDataService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeographicalData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((geographicalData: HttpResponse<GeographicalData>) => {
          if (geographicalData.body) {
            return of(geographicalData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GeographicalData());
  }
}

export const geographicalDataRoute: Routes = [
  {
    path: '',
    component: GeographicalDataComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ePaymentApp.geographicalData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeographicalDataDetailComponent,
    resolve: {
      geographicalData: GeographicalDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.geographicalData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeographicalDataUpdateComponent,
    resolve: {
      geographicalData: GeographicalDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.geographicalData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeographicalDataUpdateComponent,
    resolve: {
      geographicalData: GeographicalDataResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.geographicalData.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
