import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ScoreService } from 'app/entities/score/score.service';
import { IScore, Score } from 'app/shared/model/score.model';

describe('Service Tests', () => {
  describe('Score Service', () => {
    let injector: TestBed;
    let service: ScoreService;
    let httpMock: HttpTestingController;
    let elemDefault: IScore;
    let expectedResult: IScore | IScore[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScoreService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Score(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Score', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Score())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Score', () => {
        const returnedFromService = Object.assign(
          {
            tenMon: 'BBBBBB',
            maMon: 'BBBBBB',
            tc: 1,
            ktPercent: 1,
            thiPercent: 1,
            diemChuyenCan: 1,
            diemQuaTrinh: 1,
            thi10: 1,
            tk110: 1,
            tk10: 1,
            tk1ch: 'BBBBBB',
            tkch: 'BBBBBB',
            studentId: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Score', () => {
        const returnedFromService = Object.assign(
          {
            tenMon: 'BBBBBB',
            maMon: 'BBBBBB',
            tc: 1,
            ktPercent: 1,
            thiPercent: 1,
            diemChuyenCan: 1,
            diemQuaTrinh: 1,
            thi10: 1,
            tk110: 1,
            tk10: 1,
            tk1ch: 'BBBBBB',
            tkch: 'BBBBBB',
            studentId: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Score', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
