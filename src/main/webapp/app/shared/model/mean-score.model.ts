export interface IMeanScore {
  id?: number;
  diemtbhc10?: number;
  diemtbhc4?: number;
  diemtbtl10?: number;
  diemtbtl4?: number;
  sotinchidat?: number;
  sotinchitichluy?: number;
  phanLoai?: string;
  type?: number;
  studentId?: number;
}

export class MeanScore implements IMeanScore {
  constructor(
    public id?: number,
    public diemtbhc10?: number,
    public diemtbhc4?: number,
    public diemtbtl10?: number,
    public diemtbtl4?: number,
    public sotinchidat?: number,
    public sotinchitichluy?: number,
    public phanLoai?: string,
    public type?: number,
    public studentId?: number
  ) {}
}
