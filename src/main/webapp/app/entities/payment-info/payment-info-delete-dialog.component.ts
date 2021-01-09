import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentInfo } from 'app/shared/model/payment-info.model';
import { PaymentInfoService } from './payment-info.service';

@Component({
  templateUrl: './payment-info-delete-dialog.component.html',
})
export class PaymentInfoDeleteDialogComponent {
  paymentInfo?: IPaymentInfo;

  constructor(
    protected paymentInfoService: PaymentInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentInfoListModification');
      this.activeModal.close();
    });
  }
}
