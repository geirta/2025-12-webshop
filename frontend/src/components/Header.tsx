import { Link } from "react-router-dom";
// JavaScripti: useNavigate
// HTMLi: Link, Route, Routes

import { useTranslation } from 'react-i18next';
import { useContext } from "react";
import { CartSumContext } from "../context/CartSumContext";
import { AuthContext } from "../context/AuthContext";
import { useAppSelector } from "../store/store";


const Header = () => {
    const { t, i18n } = useTranslation();
    const {sum} = useContext(CartSumContext);
    const {isLoggedIn} = useContext(AuthContext);
    const count = useAppSelector(state => state.counter.value)
    const {handleLogout} = useContext(AuthContext);
    const {handleLogin} = useContext(AuthContext);
    const {person} = useContext(AuthContext);

    function updateLanguage(newLang: string) {
        i18n.changeLanguage(newLang);
        localStorage.setItem("language", newLang);
    }

    return (
        <div>
            <header>
                <nav className='navbar navbar-expand-lg navbar-light bg-light'>
                    <div className='container-fluid'>
                        <Link className='navbar-brand' to="/">Veebipood</Link>
                        <button className='navbar-toggler' type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span className='navbar-toggler-icon'></span>
                        </button>
                        <div className='collapse navbar-collapse' id="navbarNav">
                            <ul className='navbar-nav'>
                                <li className='nav-item'>
                                    <Link className='nav-link' aria-current="page" to="/products">{t('header.products')}</Link>
                                </li>
                                {isLoggedIn && (
                                    <>
                                        {person.role === "SUPERADMIN" && 
                                            <li className='nav-item'>
                                              <Link className='nav-link' to="/persons">{t('header.persons')}</Link>
                                          </li>
                                        }
                                        {(person.role === "ADMIN" || person.role === "SUPERADMIN") && 
                                            <li className='nav-item'>
                                                <Link className='nav-link' to="/admin">Admin</Link>
                                            </li>
                                        }
                                        
                                        <li className="nav-item">
                                            <Link className='nav-link' to="/my-orders">My orders</Link>
                                        </li>
                                    </>
                                )}
                                
                            </ul>
                        </div>

                        <div className='collapse navbar-collapse' id="navbarNav">
                            <ul className='navbar-nav'>
                                <li className="nav-item">
                                    <Link className='nav-link' to="/cart">[Cart {sum.toFixed(2)}€ / {count} pcs]</Link>
                                </li>
                                {isLoggedIn && (
                                <>
                                    <li className="nav-item">
                                        <div>Hi, {person.firstName}</div>
                                    </li>
                                    <li className="nav-item">
                                        <button className='nav-link' onClick={handleLogout}>Logout</button>
                                    </li>
                                </>
                                )}
                                {!isLoggedIn && (
                                <>
                                    <li className="nav-item">
                                        <Link className='nav-link' to="/login">Login</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className='nav-link' to="/signup">Signup</Link>
                                    </li>
                                </>
                                )}
                                <li className='nav-item'>
                                    <button className="btn btn-secondary" onClick={() => updateLanguage("et")}>ET</button>
                                </li>
                                <li className='nav-item'>
                                    <button className="btn btn-success" onClick={() => updateLanguage("en")}>EN</button>
                                </li>
                                
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>
        </div>
    )
}

export default Header