import { useState, type ReactNode } from "react";
import { AuthContext } from "./AuthContext";

export const AuthContextProvider = ({children}: {children: ReactNode}) => {
  
  const [isLoggedIn, setLoggedIn] = useState(sessionStorage.getItem("login") !== null);

  return (
    <AuthContext.Provider value={{isLoggedIn, setLoggedIn}}>
      {children}
    </AuthContext.Provider>
  )
}