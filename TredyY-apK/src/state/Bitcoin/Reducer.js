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
  
  const initialState = {
    coinList: [],
    top50: [],
    searchCoinList: [],
    marketChart: { data: [], loading: false },
    coinById: null,
    bitcoinDetails: null,
    loading: false,
    error: null,
  };
  
  const bitcoinReducer = (state = initialState, action) => {
    switch (action.type) {
      case FETCH_BITCOIN_LIST_REQUEST:
      case FETCH_TOP50_BITCOIN_REQUEST:
      case FETCH_MARKET_CHART_REQUEST:
      case FETCH_BITCOIN_BY_ID_REQUEST:
      case FETCH_BITCOIN_DETAIL_REQUEST:
      case FETCH_SEARCH_COIN_REQUEST:
        return { ...state, loading: true, error: null };
  
      case FETCH_BITCOIN_LIST_SUCCESS:
        return { ...state, loading: false, coinList: action.payload, error: null };
  
      case FETCH_TOP50_BITCOIN_SUCCESS:
        return { ...state, loading: false, top50: action.payload, error: null };
  
      case FETCH_MARKET_CHART_SUCCESS:
        return { ...state, loading: false, marketChart: { data: action.payload, loading: false }, error: null };
  
      case FETCH_BITCOIN_BY_ID_SUCCESS:
        return { ...state, loading: false, coinById: action.payload, error: null };
  
      case FETCH_BITCOIN_DETAIL_SUCCESS:
        return { ...state, loading: false, bitcoinDetails: action.payload, error: null };
  
      case FETCH_SEARCH_COIN_SUCCESS:
        return { ...state, loading: false, searchCoinList: action.payload, error: null };
  
      case FETCH_BITCOIN_LIST_FAILURE:
      case FETCH_TOP50_BITCOIN_FAILURE:
      case FETCH_MARKET_CHART_FAILURE:
      case FETCH_BITCOIN_BY_ID_FAILURE:
      case FETCH_BITCOIN_DETAIL_FAILURE:
      case FETCH_SEARCH_COIN_FAILURE:
        return { ...state, loading: false, error: action.payload };
  
      default:
        return state;
    }
  };
  
  export default bitcoinReducer;
  