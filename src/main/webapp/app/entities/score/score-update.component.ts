import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScore, Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';

@Component({
  selector: 'jhi-score-update',
  templateUrl: './score-update.component.html'
})
export class ScoreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenMon: [],
    maMon: [],
    tc: [],
    ktPercent: [],
    thiPercent: [],
    diemChuyenCan: [],
    diemQuaTrinh: [],
    thi10: [],
    tk110: [],
    tk10: [],
    tk1ch: [],
    tkch: [],
    studentId: []
  });

  constructor(protected scoreService: ScoreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ score }) => {
      this.updateForm(score);
    });
  }

  updateForm(score: IScore): void {
    this.editForm.patchValue({
      id: score.id,
      tenMon: score.tenMon,
      maMon: score.maMon,
      tc: score.tc,
      ktPercent: score.ktPercent,
      thiPercent: score.thiPercent,
      diemChuyenCan: score.diemChuyenCan,
      diemQuaTrinh: score.diemQuaTrinh,
      thi10: score.thi10,
      tk110: score.tk110,
      tk10: score.tk10,
      tk1ch: score.tk1ch,
      tkch: score.tkch,
      studentId: score.studentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const score = this.createFromForm();
    if (score.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreService.update(score));
    } else {
      this.subscribeToSaveResponse(this.scoreService.create(score));
    }
  }

  private createFromForm(): IScore {
    return {
      ...new Score(),
      id: this.editForm.get(['id'])!.value,
      tenMon: this.editForm.get(['tenMon'])!.value,
      maMon: this.editForm.get(['maMon'])!.value,
      tc: this.editForm.get(['tc'])!.value,
      ktPercent: this.editForm.get(['ktPercent'])!.value,
      thiPercent: this.editForm.get(['thiPercent'])!.value,
      diemChuyenCan: this.editForm.get(['diemChuyenCan'])!.value,
      diemQuaTrinh: this.editForm.get(['diemQuaTrinh'])!.value,
      thi10: this.editForm.get(['thi10'])!.value,
      tk110: this.editForm.get(['tk110'])!.value,
      tk10: this.editForm.get(['tk10'])!.value,
      tk1ch: this.editForm.get(['tk1ch'])!.value,
      tkch: this.editForm.get(['tkch'])!.value,
      studentId: this.editForm.get(['studentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>): void {
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
