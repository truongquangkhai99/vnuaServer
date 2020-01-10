import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VnuaServerTestModule } from '../../../test.module';
import { MeanScoreUpdateComponent } from 'app/entities/mean-score/mean-score-update.component';
import { MeanScoreService } from 'app/entities/mean-score/mean-score.service';
import { MeanScore } from 'app/shared/model/mean-score.model';

describe('Component Tests', () => {
  describe('MeanScore Management Update Component', () => {
    let comp: MeanScoreUpdateComponent;
    let fixture: ComponentFixture<MeanScoreUpdateComponent>;
    let service: MeanScoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VnuaServerTestModule],
        declarations: [MeanScoreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeanScoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeanScoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeanScoreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeanScore(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeanScore();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
