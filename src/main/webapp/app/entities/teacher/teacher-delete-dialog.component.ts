import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';

@Component({
  templateUrl: './teacher-delete-dialog.component.html'
})
export class TeacherDeleteDialogComponent {
  teacher?: ITeacher;

  constructor(protected teacherService: TeacherService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teacherService.delete(id).subscribe(() => {
      this.eventManager.broadcast('teacherListModification');
      this.activeModal.close();
    });
  }
}
