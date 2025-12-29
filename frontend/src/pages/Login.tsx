

// KOJU: Login ---> contexti muutuja trueks
// Header.tsx ---> loeb Contexti muutujat ja kuvab/peidab midagi
// Header.tsx ---> võimaldab muuta Contexti muutuja false-ks

// localStorage-sse array-na:
// Rendipoes võetud filmid (enne Start-Rentalisse backi saatmist)

import { useContext, useState } from 'react'
import { AuthContext } from '../context/AuthContext';
import { Link } from 'react-router-dom';

function Login() {
  
  const {setLoggedIn} = useContext(AuthContext);
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  function logIn() {
    sessionStorage.setItem('login', JSON.stringify({user: userName}));
    setLoggedIn(true);
  }

  return (
    <div>
      <div>
        <label>Username</label>
        <input value={userName} onChange={e => setUserName(e.target.value)}></input>
      </div>
      <div>
        <label>Password</label>
        <input value={password} onChange={e => setPassword(e.target.value)}></input>
      </div>
      <Link onClick={logIn} to="/"><button>Login</button></Link>
    </div>
  )
}

export default Login