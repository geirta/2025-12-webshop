
import { useState } from 'react'
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const backendUrl = import.meta.env.VITE_API_HOST;

function Signup() {
  
  // const {handleLogin} = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  function signUp() {
    //sessionStorage.setItem('token', JSON.stringify({email: email}));
    fetch(backendUrl + "/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({email, password})
    })
    .then(res => res.json())
    .then(json => {
      if (json.id) {
        navigate("/login")
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
      <button onClick={signUp}>Signup</button>
    </div>
  )
}

export default Signup