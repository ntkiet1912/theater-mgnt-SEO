import {
  getToken,
  removeToken,
  setToken
} from "./localStorageService";
import httpClient from "../configurations/httpClient";
import {
  API
} from "../configurations/configuration";

export const login = async (username: string, password: string) => {
  const response = await httpClient.post(API.LOGIN, {
    username: username,
    password: password,
  });

  setToken(response.data?.result?.token);

  return response;
};

export const logOut = () => {
  removeToken();
};

export const isAuthenticated = () => {
  return getToken();
};