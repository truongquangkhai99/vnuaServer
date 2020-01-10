import { Moment } from 'moment';

export interface IStudent {
  id?: number;
  studentID?: number;
  fullname?: string;
  sex?: string;
  birthDay?: Moment;
  birthPlace?: string;
  chuyenNganh?: string;
  lop?: string;
  khoa?: string;
  khoaHoc?: string;
  heDaoTao?: string;
  coVanHocTap?: string;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public studentID?: number,
    public fullname?: string,
    public sex?: string,
    public birthDay?: Moment,
    public birthPlace?: string,
    public chuyenNganh?: string,
    public lop?: string,
    public khoa?: string,
    public khoaHoc?: string,
    public heDaoTao?: string,
    public coVanHocTap?: string
  ) {}
}
