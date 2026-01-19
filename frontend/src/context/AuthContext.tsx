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
  handleLogin: (token: string) => {console.log(token)},
  handleLogout: () => {}
});
