import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IActivityInfo, defaultValue } from 'app/shared/model/activity-info.model';

export const ACTION_TYPES = {
  FETCH_ACTIVITYINFO_LIST: 'activityInfo/FETCH_ACTIVITYINFO_LIST',
  FETCH_ACTIVITYINFO: 'activityInfo/FETCH_ACTIVITYINFO',
  CREATE_ACTIVITYINFO: 'activityInfo/CREATE_ACTIVITYINFO',
  UPDATE_ACTIVITYINFO: 'activityInfo/UPDATE_ACTIVITYINFO',
  DELETE_ACTIVITYINFO: 'activityInfo/DELETE_ACTIVITYINFO',
  RESET: 'activityInfo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IActivityInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ActivityInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: ActivityInfoState = initialState, action): ActivityInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACTIVITYINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACTIVITYINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACTIVITYINFO):
    case REQUEST(ACTION_TYPES.UPDATE_ACTIVITYINFO):
    case REQUEST(ACTION_TYPES.DELETE_ACTIVITYINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACTIVITYINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACTIVITYINFO):
    case FAILURE(ACTION_TYPES.CREATE_ACTIVITYINFO):
    case FAILURE(ACTION_TYPES.UPDATE_ACTIVITYINFO):
    case FAILURE(ACTION_TYPES.DELETE_ACTIVITYINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVITYINFO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVITYINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACTIVITYINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_ACTIVITYINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACTIVITYINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/activity-infos';

// Actions

export const getEntities: ICrudGetAllAction<IActivityInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVITYINFO_LIST,
    payload: axios.get<IActivityInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IActivityInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVITYINFO,
    payload: axios.get<IActivityInfo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IActivityInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACTIVITYINFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IActivityInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACTIVITYINFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IActivityInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACTIVITYINFO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
