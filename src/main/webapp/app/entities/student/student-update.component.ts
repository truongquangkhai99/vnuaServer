import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  birthDayDp: any;

  editForm = this.fb.group({
    id: [],
    studentID: [],
    fullname: [],
    sex: [],
    birthDay: [],
    birthPlace: [],
    chuyenNganh: [],
    lop: [],
    khoa: [],
    khoaHoc: [],
    heDaoTao: [],
    coVanHocTap: []
  });

  constructor(protected studentService: StudentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      studentID: student.studentID,
      fullname: student.fullname,
      sex: student.sex,
      birthDay: student.birthDay,
      birthPlace: student.birthPlace,
      chuyenNganh: student.chuyenNganh,
      lop: student.lop,
      khoa: student.khoa,
      khoaHoc: student.khoaHoc,
      heDaoTao: student.heDaoTao,
      coVanHocTap: student.coVanHocTap
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      studentID: this.editForm.get(['studentID'])!.value,
      fullname: this.editForm.get(['fullname'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      birthDay: this.editForm.get(['birthDay'])!.value,
      birthPlace: this.editForm.get(['birthPlace'])!.value,
      chuyenNganh: this.editForm.get(['chuyenNganh'])!.value,
      lop: this.editForm.get(['lop'])!.value,
      khoa: this.editForm.get(['khoa'])!.value,
      khoaHoc: this.editForm.get(['khoaHoc'])!.value,
      heDaoTao: this.editForm.get(['heDaoTao'])!.value,
      coVanHocTap: this.editForm.get(['coVanHocTap'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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
