export interface ITeacher {
  id?: number;
  teacherID?: string;
  fullname?: string;
}

export class Teacher implements ITeacher {
  constructor(public id?: number, public teacherID?: string, public fullname?: string) {}
}
