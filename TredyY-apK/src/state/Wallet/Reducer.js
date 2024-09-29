import * as types from "./ActionType";

const initialState = {
  userWallet: {}, // Stores user wallet data
  transactions: [], // Stores wallet transactions
  loading: false, // Loading state for async actions
  error: null, // Stores error messages, if any
};

const walletReducer = (state = initialState, action) => {
  switch (action.type) {
    // Handle loading state for all requests
    case types.GET_USER_WALLET_REQUEST:
    case types.DEPOSIT_MONEY_REQUEST:
    case types.TRANSFER_MONEY_REQUEST:
    case types.GET_WALLET_TRANSACTION_REQUEST:
      return {
        ...state,
        loading: true, // Set loading to true
        error: null, // Clear any previous errors
      };

    // Handle success for fetching user wallet
    case types.GET_USER_WALLET_SUCCESS:
      return {
        ...state,
        userWallet: action.payload, // Update user wallet data with the response
        loading: false, // Set loading to false
        error: null, // Clear errors
      };

    // Handle success for fetching wallet transactions
    case types.GET_WALLET_TRANSACTION_SUCCESS:
      return {
        ...state,
        transactions: action.payload, // Update transactions with the response
        loading: false, // Set loading to false
        error: null, // Clear errors
      };

    // Handle success for depositing money
    case types.DEPOSIT_MONEY_SUCCESS:
      return {
        ...state,
        userWallet: action.payload, // Update wallet after depositing money
        loading: false, // Set loading to false
        error: null, // Clear errors
      };

    // Handle success for transferring money
    case types.TRANSFER_MONEY_SUCCESS:
      return {
        ...state,
        userWallet: action.payload, // Update wallet after transferring money
        loading: false, // Set loading to false
        error: null, // Clear errors
      };

    // Handle failure for all operations
    case types.GET_USER_WALLET_FAILURE:
    case types.DEPOSIT_MONEY_FAILURE:
    case types.TRANSFER_MONEY_FAILURE:
    case types.GET_WALLET_TRANSACTION_FAILURE:
      return {
        ...state,
        loading: false, // Set loading to false
        error: action.error, // Update error message from action
      };

    // Default case to return the current state if no action matches
    default:
      return state;
  }
};

export default walletReducer;
