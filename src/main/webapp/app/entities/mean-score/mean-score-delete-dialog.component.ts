import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeanScore } from 'app/shared/model/mean-score.model';
import { MeanScoreService } from './mean-score.service';

@Component({
  templateUrl: './mean-score-delete-dialog.component.html'
})
export class MeanScoreDeleteDialogComponent {
  meanScore?: IMeanScore;

  constructor(protected meanScoreService: MeanScoreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meanScoreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('meanScoreListModification');
      this.activeModal.close();
    });
  }
}
