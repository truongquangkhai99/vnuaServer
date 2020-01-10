export interface IScore {
  id?: number;
  tenMon?: string;
  maMon?: string;
  tc?: number;
  ktPercent?: number;
  thiPercent?: number;
  diemChuyenCan?: number;
  diemQuaTrinh?: number;
  thi10?: number;
  tk110?: number;
  tk10?: number;
  tk1ch?: string;
  tkch?: string;
  studentId?: number;
}

export class Score implements IScore {
  constructor(
    public id?: number,
    public tenMon?: string,
    public maMon?: string,
    public tc?: number,
    public ktPercent?: number,
    public thiPercent?: number,
    public diemChuyenCan?: number,
    public diemQuaTrinh?: number,
    public thi10?: number,
    public tk110?: number,
    public tk10?: number,
    public tk1ch?: string,
    public tkch?: string,
    public studentId?: number
  ) {}
}
