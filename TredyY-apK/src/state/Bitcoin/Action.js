import axios from "axios";
import {
  FETCH_BITCOIN_LIST_REQUEST,
  FETCH_BITCOIN_LIST_SUCCESS,
  FETCH_BITCOIN_LIST_FAILURE,
  FETCH_TOP50_BITCOIN_REQUEST,
  FETCH_TOP50_BITCOIN_SUCCESS,
  FETCH_TOP50_BITCOIN_FAILURE,
  FETCH_MARKET_CHART_REQUEST,
  FETCH_MARKET_CHART_SUCCESS,
  FETCH_MARKET_CHART_FAILURE,
  FETCH_BITCOIN_BY_ID_REQUEST,
  FETCH_BITCOIN_BY_ID_SUCCESS,
  FETCH_BITCOIN_BY_ID_FAILURE,
  FETCH_BITCOIN_DETAIL_REQUEST,
  FETCH_BITCOIN_DETAIL_SUCCESS,
  FETCH_BITCOIN_DETAIL_FAILURE,
  FETCH_SEARCH_COIN_REQUEST,
  FETCH_SEARCH_COIN_SUCCESS,
  FETCH_SEARCH_COIN_FAILURE,
} from "./ActionType";

const baseUrl = "http://localhost:5055/coins";

export const getBitcoinList = (page) => async (dispatch) => {
  dispatch({ type: FETCH_BITCOIN_LIST_REQUEST });
  try {
    const { data } = await axios.get(`${baseUrl}?page=${page}`);
    dispatch({ type: FETCH_BITCOIN_LIST_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_BITCOIN_LIST_FAILURE, payload: err.message });
  }
};

export const getTop50BitcoinList = () => async (dispatch) => {
  dispatch({ type: FETCH_TOP50_BITCOIN_REQUEST });
  try {
    const { data } = await axios.get(`${baseUrl}/top50`);
    dispatch({ type: FETCH_TOP50_BITCOIN_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_TOP50_BITCOIN_FAILURE, payload: err.message });
  }
};

export const fetchMarketChart = (bitcoinId, days) => async (dispatch) => {
  dispatch({ type: FETCH_MARKET_CHART_REQUEST });
  try {
    const token = localStorage.getItem('jwt');
    
    // Ensure bitcoinId is correctly passed as a string or valid value
    const id = typeof bitcoinId === "string" ? bitcoinId : bitcoinId?.id || null;
    
    if (!id) {
      throw new Error("Invalid bitcoinId");
    }

    const { data } = await axios.get(`${baseUrl}/${id}/chart?days=${days}`, {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization Bearer Token
      },
    });
    dispatch({ type: FETCH_MARKET_CHART_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_MARKET_CHART_FAILURE, payload: err.message });
  }
};


export const fetchBitcoinById = (bitcoinId) => async (dispatch) => {
  dispatch({ type: FETCH_BITCOIN_BY_ID_REQUEST });
  try {
    const { data } = await axios.get(`${baseUrl}/${bitcoinId}`);
    dispatch({ type: FETCH_BITCOIN_BY_ID_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_BITCOIN_BY_ID_FAILURE, payload: err.message });
  }
};

export const fetchBitcoinDetails = (bitcoinId) => async (dispatch) => {
  dispatch({ type: FETCH_BITCOIN_DETAIL_REQUEST });
  try {
    const { data } = await axios.get(`${baseUrl}/bitCoinDetails/${bitcoinId}`);
    dispatch({ type: FETCH_BITCOIN_DETAIL_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_BITCOIN_DETAIL_FAILURE, payload: err.message });
  }
};


export const searchBitcoin = (keyword) => async (dispatch) => {
  dispatch({ type: FETCH_SEARCH_COIN_REQUEST });
  try {
    const { data } = await axios.get(`${baseUrl}/search?q=${keyword}`);
    dispatch({ type: FETCH_SEARCH_COIN_SUCCESS, payload: data });
  } catch (err) {
    dispatch({ type: FETCH_SEARCH_COIN_FAILURE, payload: err.message });
  }
};
