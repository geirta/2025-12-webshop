import { createContext } from "react";

export const AuthContext = createContext({
  isLoggedIn: false,
  person: {
      firstName: "",
      lastName: "",
      email: "",
      personalCode: "",
      phone: "",
      role: "CUSTOMER"
  },
  handleLogin: (token: string, expires: number) => {console.log(token); console.log(expires)},
  handleLogout: () => {}
});
