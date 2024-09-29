import api from "../api";
import * as types from "./ActionType";

export const payOrder = ({ jwt, orderData, amount }) => async (dispatch) => {
    dispatch({ type: types.PAY_ORDER_REQUEST });
    try {
      // Ensure bitCoinId is present in the orderData object
      const response = await api.post(`/api/order/pay`, orderData, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
  
      dispatch({
        type: types.PAY_ORDER_SUCCESS,
        payload: response.data,
        amount,
      });
      console.log("Order success:", response.data);
    } catch (error) {
      console.log("Error:", error);
      dispatch({
        type: types.PAY_ORDER_FAILURE,
        error: error.message,
      });
    }
  };
  

//   export const getOrderById=(jwt,orderId) => async(dispatch) =>{
//     dispatch({type: types.GET_ORDER_REQUEST})
//   }

export const getAllOrdersForUser =
  ({ jwt, orderType, assetSymbol }) =>
  async (dispatch) => {
    dispatch({ type: types.GET_ALL_ORDERS_REQUEST });
    try {
      const response = await api.get("/api/order/orders", {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
        params: {
          order_type: orderType,
          asset_symbol: assetSymbol,
        },
      });
      dispatch({
        type: types.GET_ALL_ORDERS_SUCCESS,
        payload: response.data,
      }),
        console.log("order success : ", response.data);
    } catch (error) {
      dispatch({
        type: types.GET_ALL_ORDERS_FAILURE,
        error: error.message,
      });
    }
  };
