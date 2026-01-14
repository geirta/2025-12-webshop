
import { Navigate, Route, Routes } from 'react-router-dom'
import './App.css'
import Footer from './components/Footer'
import Header from './components/Header'
import Home from './pages/Home'
import PersonDetails from './pages/PersonDetails'
import Products from './pages/Products'
import Persons from './pages/admin/Persons'
import MyOrders from './pages/MyOrders'
import Cart from './pages/Cart'
import { AuthContext } from './context/AuthContext'
import Login from './pages/Login'
import { useContext } from 'react'
import ProductDetails from './pages/ProductDetails'
import CategoriesManage from './pages/admin/CategoriesManage'
import ProductAdd from './pages/admin/ProductAdd'
import ProductEdit from './pages/admin/ProductEdit'
import ProductManage from './pages/admin/ProductManage'
import AdminHome from './pages/admin/AdminHome'
import NotFound from './pages/NotFound'
import CheckPayment from './pages/CheckPayment'

function App() {

  const {isLoggedIn} = useContext(AuthContext);

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
          <Route path='/product/:product_id' element={<ProductDetails />}></Route>
          <Route path='/persons/:id' element={<PersonDetails/>}></Route>
          <Route path='/payment' element={<CheckPayment/>}></Route>

          <Route path='/persons' element={<Persons/>}></Route>

          <Route path='/admin' element={<AdminHome />}></Route>
          <Route path='/admin/manage-categories' element={<CategoriesManage />}></Route>
          <Route path='/admin/persons' element={<Persons/>}></Route>
          <Route path='/admin/add-product' element={<ProductAdd />}></Route>
          <Route path='/admin/edit-product/:product_id' element={<ProductEdit />}></Route>
          <Route path='/admin/manage-products' element={<ProductManage />}></Route>

          
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/cart' element={<Cart/>}></Route>
          <Route path='/my-orders' element={isLoggedIn ? <MyOrders/> : <Navigate to='/login' />}></Route>

          <Route path='/*' element={<NotFound/>}></Route>
        </Routes>

        <Footer />
    </>
  )
}

export default App
