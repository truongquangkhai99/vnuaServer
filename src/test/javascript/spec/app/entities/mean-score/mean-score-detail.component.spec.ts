import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VnuaServerTestModule } from '../../../test.module';
import { MeanScoreDetailComponent } from 'app/entities/mean-score/mean-score-detail.component';
import { MeanScore } from 'app/shared/model/mean-score.model';

describe('Component Tests', () => {
  describe('MeanScore Management Detail Component', () => {
    let comp: MeanScoreDetailComponent;
    let fixture: ComponentFixture<MeanScoreDetailComponent>;
    const route = ({ data: of({ meanScore: new MeanScore(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VnuaServerTestModule],
        declarations: [MeanScoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeanScoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeanScoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load meanScore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meanScore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
