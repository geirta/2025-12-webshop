import { useEffect, useState } from "react";

const backendUrl = import.meta.env.VITE_API_HOST;

export function useFetch<T>({endpoint}: {endpoint: string}) {
  const [items, setItems] = useState<T[]>([]);

    useEffect(() => {
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