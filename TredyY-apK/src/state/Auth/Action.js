import axios from "axios";
import {
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  LOGIN_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  GET_USER_FAILURE,
  LOGOUT
} from "./ActionType";

export const loginSuccess = (jwt) => ({
  type: LOGIN_SUCCESS,
  payload: jwt,
});

export const register = (userData) => async (dispatch) => {
  dispatch({ type: REGISTER_REQUEST });
  const baseUrl = "http://localhost:5055";

  try {
    const response = await axios.post(`${baseUrl}/auth/signUp`, userData);
    const user = response.data;

    dispatch({ type: REGISTER_SUCCESS, payload: user.jwt });
    localStorage.setItem("jwt", user.jwt);

    console.log(user);
  } catch (err) {
    dispatch({ type: REGISTER_FAILURE, payload: err.message });

    console.log(err);
  }
};

export const login = (userData) => async (dispatch) => {
  dispatch({ type: LOGIN_REQUEST });
  const baseUrl = "http://localhost:5055";

  try {
    const response = await axios.post(`${baseUrl}/auth/signIn`, userData.data);
    const user = response.data;

    dispatch({type:LOGIN_SUCCESS,payload:user.jwt})
    localStorage.setItem("jwt", user.jwt);
    userData.navigate('/');

    console.log(user);
  } catch (err) {
    dispatch({ type: LOGIN_FAILURE, payload: err.message });

    console.log(err);
  }
};

export const getUserProfile = () => async (dispatch) => {
  dispatch({ type: GET_USER_REQUEST });
  const baseUrl = "http://localhost:5055";
  const jwt = localStorage.getItem("jwt");

  try {
    const response = await axios.get(`${baseUrl}/api/user/jwt`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });

    const user = response.data;

    dispatch({ type: GET_USER_SUCCESS, payload: user });

    console.log(user);
  } catch (err) {
    dispatch({ type: GET_USER_FAILURE, payload: err.message });

    console.log(err);
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwt");

  dispatch({ type: LOGOUT });
};
