import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";

const backendUrl = import.meta.env.VITE_API_HOST;

export function useFetch<T>({endpoint}: {endpoint: string}) {
  const [items, setItems] = useState<T[]>([]);
  const {handleLogout} = useContext(AuthContext);

    useEffect(() => {
      
      const expiration = Number(sessionStorage.getItem("expires"));
      if (!sessionStorage.getItem("token") 
          || !sessionStorage.getItem("expires") 
          || Date.now() > expiration) {
        alert("Palun logi uuesti sisse!");
        handleLogout();
        return;
      }

      fetch(`${backendUrl}/` + endpoint, {
        headers: {
          "Authorization": "Bearer " + sessionStorage.getItem("token")
        }
      })
        .then(res => res.json())
        .then(json => {
            setItems(json);
        });
    }, [endpoint]);

  return (
    items
  )
}