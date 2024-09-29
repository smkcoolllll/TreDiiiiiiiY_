// src/api.js
import axios from "axios";

// Base URL for your API
const baseUrl = "http://localhost:5055";

// Create an Axios instance
const api = axios.create({
  baseURL: baseUrl,
  timeout: 10000, // 10 seconds timeout, you can change this as needed
});

// You can also add request interceptors for adding JWT tokens or handling errors globally
api.interceptors.request.use(
  (config) => {
    // You can set headers or perform other logic before the request is sent
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
