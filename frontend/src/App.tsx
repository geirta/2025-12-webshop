
import { Route, Routes } from 'react-router-dom'
import './App.css'
import Footer from './components/Footer'
import Header from './components/Header'
import Home from './pages/Home'
import PersonDetails from './pages/PersonDetails'
import Products from './pages/Products'
import Persons from './pages/Persons'

function App() {

  // Rendipood:
  // - erinevad lehed (route-mine)
  // - tõlge
  // - darkmode/lightmode + localStorage
  // - https://fonts.google.com/
  // - MUI/Tailwind/Bootstrap
  // - soovi korral kümnevõistlus
  // - https://www.npmjs.com/package/react-toastify
  // - https://react-hot-toast.com/

  return (
    <>
        <Header />

        <Routes>
          <Route path='/' element={<Home/>}></Route>
          <Route path='/products' element={<Products/>}></Route>
          <Route path='/persons' element={<Persons/>}></Route>
          <Route path='/persons/:id' element={<PersonDetails/>}></Route>
        </Routes>

        <Footer />
    </>
  )
}

export default App
