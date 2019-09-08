export interface IActivityInfo {
  id?: number;
  activityName?: string;
  user?: string;
  telephone?: string;
  payStatus?: string;
  addDate?: string;
}

export const defaultValue: Readonly<IActivityInfo> = {};
