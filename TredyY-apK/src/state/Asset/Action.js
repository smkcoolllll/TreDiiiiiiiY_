import api from "../api";
import * as types from "./ActionType";

export const getAssetById =
  ({ assetId, jwt }) =>
  async (dispatch) => {
    dispatch({ type: types.GET_ASSET_REQUEST });
    try {
      const response = await api.get(`/api/asset/${assetId}`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      dispatch({
        type: types.GET_ASSET_SUCCESS,
        payload: response.data,
      });
      console.log("Asset by id : ", response.data);
    } catch (error) {
      dispatch({
        type: types.GET_ASSET_FAILURE,
        error: error.message,
      });
    }
  };

export const getAssetDetails =
  ({ bitcoinId, jwt }) =>
  async (dispatch) => {
    dispatch({ type: types.GET_ASSET_DETAILS_REQUEST });
    try {
      const response = await api.get(`/api/asset/bitCoin/${bitcoinId}/user`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
      dispatch({
        type: types.GET_ASSET_DETAILS_SUCCESS,
        payload: response.data,
      });
      console.log("asset deatails :", response.data);
    } catch (error) {
      dispatch({ tyep: types.GET_ASSET_DETAILS_FAILURE, error: error.message });
    }
  };

export const getUserAssets = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_USER_ASSET_REQUEST });
  try {
    const response = await api.get(`/api/asset/Assets`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    dispatch({
      type: types.GET_USER_ASSET_SUCCESS,
      payload: response.data,
    });
    console.log("user asset : ", response.data);
  } catch (error) {
    dispatch({
      type: types.GET_USER_ASSET_FAILURE,
      error: error.message,
    });
  }
};
