export interface ITeacher {
  id?: number;
  teacherID?: number;
  fullname?: string;
}

export class Teacher implements ITeacher {
  constructor(public id?: number, public teacherID?: number, public fullname?: string) {}
}
