
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import PersonsComponent from './components/PersonsComponent'
import ListProductsComponent from './components/ListProductsComponent'
import HomeComponent from './components/HomeComponent'
import PersonDetailsComponent from './components/PersonDetailsComponent'

function App() {


  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
          <Routes>
            <Route path='/' element={<HomeComponent/>}></Route>
            <Route path='/products' element={<ListProductsComponent/>}></Route>
            <Route path='/persons' element={<PersonsComponent/>}></Route>
            <Route path='/persons/:id' element={<PersonDetailsComponent/>}></Route>
          </Routes>
          
        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
