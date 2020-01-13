import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VnuaServerTestModule } from '../../../test.module';
import { TeacherDetailComponent } from 'app/entities/teacher/teacher-detail.component';
import { Teacher } from 'app/shared/model/teacher.model';

describe('Component Tests', () => {
  describe('Teacher Management Detail Component', () => {
    let comp: TeacherDetailComponent;
    let fixture: ComponentFixture<TeacherDetailComponent>;
    const route = ({ data: of({ teacher: new Teacher(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VnuaServerTestModule],
        declarations: [TeacherDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TeacherDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeacherDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load teacher on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teacher).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
