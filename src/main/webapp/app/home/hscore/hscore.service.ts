import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { ITeacher } from 'app/shared/model/teacher.model';

type EntityArrayResponseType = HttpResponse<ITeacher[]>;

@Injectable({ providedIn: 'root' })
export class HScoreService {
  public resourceGetTopStudentsURL = SERVER_API_URL + 'api/get-top-students';
  public resourceGetClassesByStudentIDURL = SERVER_API_URL + 'api/public/classes-by-student-id';
  public resourceGetStudentRankURL = SERVER_API_URL + 'api/public/student-rank';

  constructor(protected http: HttpClient) {}

  getTopStudents(): Observable<EntityArrayResponseType> {
    return this.http.get<any[]>(`${this.resourceGetTopStudentsURL}`, {
      observe: 'response'
    });
  }

  getClassesByStudentID(studentID: any): Observable<EntityArrayResponseType> {
    return this.http.get<any[]>(`${this.resourceGetClassesByStudentIDURL}/${studentID}`, {
      observe: 'response'
    });
  }

  getRankByStudentID(studentID: any): Observable<EntityArrayResponseType> {
    return this.http.get<any>(`${this.resourceGetStudentRankURL}/${studentID}`, {
      observe: 'response'
    });
  }
}
