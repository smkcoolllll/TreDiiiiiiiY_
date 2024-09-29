import * as types from './ActionType';
import api from '../api';

// Withdrawal Request
export const withdrawalRequest = (amount, jwt) => async (dispatch) => {
  dispatch({ type: types.WITHDRAWAL_REQUEST });

  try {
    const response = await api.post(`/api/withdrawal/${amount}`, null, {
      headers: { Authorization: `Bearer ${jwt}` }
    });

    dispatch({
      type: types.WITHDRAWAL_SUCCESS,
      payload: response.data,
    });

  } catch (error) {
    dispatch({
      type: types.WITHDRAWAL_FAILURE,
      payload: error.message,
    });
  }
};

// Proceed with Withdrawal (Admin)
export const proceedWithdrawal = (id, jwt, accept) => async (dispatch) => {
  dispatch({ type: types.WITHDRAWAL_PROCEED_REQUEST });

  try {
    const response = await api.patch(`/api/admin/withdrawal/${id}/proceed/${accept}`, null, {
      headers: { Authorization: `Bearer ${jwt}` },
    });

    dispatch({
      type: types.WITHDRAWAL_PROCEED_SUCCESS,
      payload: response.data,
    });

  } catch (error) {
    dispatch({
      type: types.WITHDRAWAL_PROCEED_FAILURE,
      payload: error.message,
    });
  }
};

// Get Withdrawal History
export const getWithdrawalHistory = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_WITHDRAWAL_HISTORY_REQUEST });

  try {
    const response = await api.get(`/api/withdrawal/get`, {
      headers: { Authorization: `Bearer ${jwt}` },
    });

    dispatch({
      type: types.GET_WITHDRAWAL_HISTORY_SUCCESS,
      payload: response.data,
    });

  } catch (error) {
    dispatch({
      type: types.GET_WITHDRAWAL_HISTORY_FAILURE,
      payload: error.message,
    });
  }
};

// Get All Withdrawal Requests (Admin)
export const getAllWithdrawalRequests = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_ALL_WITHDRAWAL_REQUEST_REQUEST });

  try {
    const response = await api.get(`/api/withdrawal/getAll`, {
      headers: { Authorization: `Bearer ${jwt}` },
    });

    dispatch({
      type: types.GET_ALL_WITHDRAWAL_REQUEST_SUCCESS,
      payload: response.data,
    });

  } catch (error) {
    dispatch({
      type: types.GET_ALL_WITHDRAWAL_REQUEST_FAILURE,
      payload: error.message,
    });
  }
};

// Add Payment Details
export const addPaymentDetails = (paymentDetails, jwt) => async (dispatch) => {
  dispatch({ type: types.ADD_PAYMENT_DETAILS_REQUEST });

  try {
    const response = await api.post(`/api/paymentDetail/add/payment`, paymentDetails, {
      headers: { Authorization: `Bearer ${jwt}` },
    });
    console.log("JWT : ",jwt);
    

    dispatch({
      type: types.ADD_PAYMENT_DETAILS_SUCCESS,
      payload: response.data,
    });
    console.log("Payment Deatils : ",response.data);
    
  } catch (error) {
    dispatch({
      type: types.ADD_PAYMENT_DETAILS_FAILURE,
      payload: error.message,
    });
  }
};

// Get Payment Details
export const getPaymentDetails = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_PAYMENT_DETAILS_REQUEST });

  try {
    const response = await api.get(`/api/paymentDetail/get/payment`, {
      headers: { Authorization: `Bearer ${jwt}` },
    });

    dispatch({
      type: types.GET_PAYMENT_DETAILS_SUCCESS,
      payload: response.data,
    });

  } catch (error) {
    dispatch({
      type: types.GET_PAYMENT_DETAILS_FAILURE,
      payload: error.message,
    });
  }
};
