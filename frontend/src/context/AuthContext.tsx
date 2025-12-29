import { createContext } from "react";

export const AuthContext = createContext({
  isLoggedIn: false,
  setLoggedIn: (newValue: boolean) => {console.log(newValue)}
});
