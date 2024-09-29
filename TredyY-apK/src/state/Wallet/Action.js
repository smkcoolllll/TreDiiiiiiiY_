import * as types from "./ActionType";
import api from "../api"; // assuming you have a pre-configured API instance

// Get User Wallet
export const getUserWallet = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_USER_WALLET_REQUEST });

  try {
    const response = await api.get(`/api/wallet/userWallet`, {
      headers: { Authorization: `Bearer ${jwt}` },
    });

    dispatch({
      type: types.GET_USER_WALLET_SUCCESS,
      payload: response.data,
    });

    console.log("user wallet : ", response.data);
  } catch (error) {
    dispatch({
      type: types.GET_USER_WALLET_FAILURE,
      error: error.message,
    });
  }
};

// Get Wallet Transactions
export const getWalletTransactions = ({ jwt, walletId }) => async (dispatch) => {
    dispatch({ type: types.GET_WALLET_TRANSACTION_REQUEST });
  
    try {
      const response = await api.get(`/api/walletTransactions/${walletId}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });
  
      dispatch({
        type: types.GET_WALLET_TRANSACTION_SUCCESS,
        payload: response.data,
      });
      console.log("Transactions: ", response.data);
    } catch (error) {
      dispatch({
        type: types.GET_WALLET_TRANSACTION_FAILURE,
        error: error.message,
      });
    }
  };
  

// Deposit Money into Wallet
export const depositMoney =
  ({ jwt, orderId, paymentId }) =>
  async (dispatch) => {
    dispatch({ type: types.DEPOSIT_MONEY_REQUEST });

    //   console.log("__________",paymentId,orderId);

    try {
      const response = await api.put("/api/wallet/wallet/deposit", null, {
        params: { order_id: orderId },
        headers: {
          Authorization: `Bearer ${jwt}`,
          payment_id: paymentId,
        },
      });

      dispatch({
        type: types.DEPOSIT_MONEY_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      dispatch({
        type: types.DEPOSIT_MONEY_FAILURE,
        error: error.message,
      });
    }
  };

// Transfer Money between Wallets
export const transferMoney =
  ({ jwt, walletId, reqData }) =>
  async (dispatch) => {
    dispatch({ type: types.TRANSFER_MONEY_REQUEST });

    try {
      const response = await api.put(
        `/api/wallet/transfer/${walletId}`,
        reqData,
        {
          headers: { Authorization: `Bearer ${jwt}` },
        }
      );

      dispatch({
        type: types.TRANSFER_MONEY_SUCCESS,
        payload: response.data,
      });
      console.log("Transfer Money : ", response.data);
    } catch (error) {
      dispatch({
        type: types.TRANSFER_MONEY_FAILURE,
        error: error.message,
      });
    }
  };
