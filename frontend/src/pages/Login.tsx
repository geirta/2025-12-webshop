

// KOJU: Login ---> contexti muutuja trueks
// Header.tsx ---> loeb Contexti muutujat ja kuvab/peidab midagi
// Header.tsx ---> võimaldab muuta Contexti muutuja false-ks

// localStorage-sse array-na:
// Rendipoes võetud filmid (enne Start-Rentalisse backi saatmist)

import { useContext, useState } from 'react'
import { AuthContext } from '../context/AuthContext';

const backendUrl = import.meta.env.VITE_API_HOST;

function Login() {
  
  const {handleLogin} = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");


  function logIn() {
    //sessionStorage.setItem('token', JSON.stringify({email: email}));
    fetch(backendUrl + "/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({email, password})
    })
    .then(res => res.json())
    .then(json => {
      if (json.token) {
        handleLogin(json.token, json.expires);
        setEmail("");
        setPassword("");
        return;
      }
      alert(json.message);
    });
  }

  return (
    <div>
      <div>
        <label>Email</label>
        <input value={email} onChange={e => setEmail(e.target.value)} />
      </div>
      <div>
        <label>Password</label>
        <input value={password} onChange={e => setPassword(e.target.value)} type="password" />
      </div>
      <button onClick={logIn}>Login</button>
    </div>
  )
}

export default Login