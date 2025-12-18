import React from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderComponent = () => {


    return (
        <div>
            <header>
                <nav className='navbar navbar-expand-lg navbar-light bg-light'>
                      <div className='container-fluid'>
                        <a className='navbar-brand' href="/">Veebipood</a>
                        <button className='navbar-toggler' type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span className='navbar-toggler-icon'></span>
                        </button>
                        <div className='collapse navbar-collapse' id="navbarNav">
                            <ul className='navbar-nav'>
                                <li className='nav-item'>
                                    <a className='nav-link' aria-current="page" href="/products">Products</a>
                                </li>
                                <li className='nav-item'>
                                    <a className='nav-link' href="/persons">Persons</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent