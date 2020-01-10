import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMeanScore } from 'app/shared/model/mean-score.model';

type EntityResponseType = HttpResponse<IMeanScore>;
type EntityArrayResponseType = HttpResponse<IMeanScore[]>;

@Injectable({ providedIn: 'root' })
export class MeanScoreService {
  public resourceUrl = SERVER_API_URL + 'api/mean-scores';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/mean-scores';

  constructor(protected http: HttpClient) {}

  create(meanScore: IMeanScore): Observable<EntityResponseType> {
    return this.http.post<IMeanScore>(this.resourceUrl, meanScore, { observe: 'response' });
  }

  update(meanScore: IMeanScore): Observable<EntityResponseType> {
    return this.http.put<IMeanScore>(this.resourceUrl, meanScore, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeanScore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeanScore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeanScore[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
