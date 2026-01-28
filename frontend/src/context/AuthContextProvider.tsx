import { useEffect, useState, type ReactNode } from "react";
import { AuthContext } from "./AuthContext";
import { useNavigate } from "react-router-dom";
import type { Person } from "../models/Person";

const backendUrl = import.meta.env.VITE_API_HOST;

export const AuthContextProvider = ({children}: {children: ReactNode}) => {
  
  const personObject: Person = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      personalCode: "",
      phone: "",
      role: "CUSTOMER",
      address: {
          city: "",
          state: "",
          country: "",
          zipcode: "",
          street: "",
          number: "",
          complement: ""
      }
  }
  const [isLoggedIn, setLoggedIn] = useState(sessionStorage.getItem("token") !== null);
  const [person, setPerson] = useState<Person>(personObject);
  const navigate = useNavigate();

  useEffect(() => {
    if (!sessionStorage.getItem("token")){
      return;
    }
    fetch(backendUrl + "/profile", {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
    .then(res => res.json())
    .then(json => setPerson(json));
  }, [isLoggedIn]);

  const handleLogin = (token: string, expires: number) => {
      sessionStorage.setItem("token", token);
      sessionStorage.setItem("expires", expires.toString());
      setLoggedIn(true);
      navigate("/profile")
  };

  const handleLogout = () => {
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("expires");
      setLoggedIn(false);
      setPerson(personObject);
      navigate("/")
  };

  return (
    <AuthContext.Provider value={{person, isLoggedIn, handleLogin, handleLogout}}>
      {children}
    </AuthContext.Provider>
  )
}