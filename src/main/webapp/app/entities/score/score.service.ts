import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IScore } from 'app/shared/model/score.model';

type EntityResponseType = HttpResponse<IScore>;
type EntityArrayResponseType = HttpResponse<IScore[]>;

@Injectable({ providedIn: 'root' })
export class ScoreService {
  public resourceUrl = SERVER_API_URL + 'api/scores';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/scores';

  constructor(protected http: HttpClient) {}

  create(score: IScore): Observable<EntityResponseType> {
    return this.http.post<IScore>(this.resourceUrl, score, { observe: 'response' });
  }

  update(score: IScore): Observable<EntityResponseType> {
    return this.http.put<IScore>(this.resourceUrl, score, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScore[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
