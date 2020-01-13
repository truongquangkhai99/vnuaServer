import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { ITeacher } from 'app/shared/model/teacher.model';

type EntityArrayResponseType = HttpResponse<ITeacher[]>;

@Injectable({ providedIn: 'root' })
export class HTeacherService {
  public resourceCustomSearchURL = SERVER_API_URL + 'api/search-teachers';

  constructor(protected http: HttpClient) {}

  customSearch(query: any): Observable<EntityArrayResponseType> {
    return this.http.get<ITeacher[]>(`${this.resourceCustomSearchURL}`, {
      params: {
        query
      },
      observe: 'response'
    });
  }
}
