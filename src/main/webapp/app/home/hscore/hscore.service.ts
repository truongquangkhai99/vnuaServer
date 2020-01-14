import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { ITeacher } from 'app/shared/model/teacher.model';

type EntityArrayResponseType = HttpResponse<ITeacher[]>;

@Injectable({ providedIn: 'root' })
export class HScoreService {
  public resourceGetTopStudentsURL = SERVER_API_URL + 'api/get-top-students';

  constructor(protected http: HttpClient) {}

  getTopStudents(): Observable<EntityArrayResponseType> {
    return this.http.get<any[]>(`${this.resourceGetTopStudentsURL}`, {
      observe: 'response'
    });
  }
}
