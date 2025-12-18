import React from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderComponent = () => {


    return (
        <div>
            <header>
                <nav className='navbar navbar-expand-lg navbar-light bg-light'>
                      <div class="container-fluid">
                        <a class="navbar-brand" href="/">Veebipood</a>
                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="/products">Products</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/persons">Persons</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/">Placeholder</a>
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