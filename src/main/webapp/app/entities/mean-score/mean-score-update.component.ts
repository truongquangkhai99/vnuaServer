import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMeanScore, MeanScore } from 'app/shared/model/mean-score.model';
import { MeanScoreService } from './mean-score.service';

@Component({
  selector: 'jhi-mean-score-update',
  templateUrl: './mean-score-update.component.html'
})
export class MeanScoreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    diemtbhc10: [],
    diemtbhc4: [],
    diemtbtl10: [],
    diemtbtl4: [],
    sotinchidat: [],
    sotinchitichluy: [],
    phanLoai: [],
    type: [],
    studentId: []
  });

  constructor(protected meanScoreService: MeanScoreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meanScore }) => {
      this.updateForm(meanScore);
    });
  }

  updateForm(meanScore: IMeanScore): void {
    this.editForm.patchValue({
      id: meanScore.id,
      diemtbhc10: meanScore.diemtbhc10,
      diemtbhc4: meanScore.diemtbhc4,
      diemtbtl10: meanScore.diemtbtl10,
      diemtbtl4: meanScore.diemtbtl4,
      sotinchidat: meanScore.sotinchidat,
      sotinchitichluy: meanScore.sotinchitichluy,
      phanLoai: meanScore.phanLoai,
      type: meanScore.type,
      studentId: meanScore.studentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meanScore = this.createFromForm();
    if (meanScore.id !== undefined) {
      this.subscribeToSaveResponse(this.meanScoreService.update(meanScore));
    } else {
      this.subscribeToSaveResponse(this.meanScoreService.create(meanScore));
    }
  }

  private createFromForm(): IMeanScore {
    return {
      ...new MeanScore(),
      id: this.editForm.get(['id'])!.value,
      diemtbhc10: this.editForm.get(['diemtbhc10'])!.value,
      diemtbhc4: this.editForm.get(['diemtbhc4'])!.value,
      diemtbtl10: this.editForm.get(['diemtbtl10'])!.value,
      diemtbtl4: this.editForm.get(['diemtbtl4'])!.value,
      sotinchidat: this.editForm.get(['sotinchidat'])!.value,
      sotinchitichluy: this.editForm.get(['sotinchitichluy'])!.value,
      phanLoai: this.editForm.get(['phanLoai'])!.value,
      type: this.editForm.get(['type'])!.value,
      studentId: this.editForm.get(['studentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeanScore>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
