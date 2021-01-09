import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentInfo, PaymentInfo } from 'app/shared/model/payment-info.model';
import { PaymentInfoService } from './payment-info.service';
import { PaymentInfoComponent } from './payment-info.component';
import { PaymentInfoDetailComponent } from './payment-info-detail.component';
import { PaymentInfoUpdateComponent } from './payment-info-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentInfoResolve implements Resolve<IPaymentInfo> {
  constructor(private service: PaymentInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentInfo: HttpResponse<PaymentInfo>) => {
          if (paymentInfo.body) {
            return of(paymentInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentInfo());
  }
}

export const paymentInfoRoute: Routes = [
  {
    path: '',
    component: PaymentInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ePaymentApp.paymentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentInfoDetailComponent,
    resolve: {
      paymentInfo: PaymentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.paymentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentInfoUpdateComponent,
    resolve: {
      paymentInfo: PaymentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.paymentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentInfoUpdateComponent,
    resolve: {
      paymentInfo: PaymentInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ePaymentApp.paymentInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
