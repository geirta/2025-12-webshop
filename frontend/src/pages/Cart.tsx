import { useContext, useState } from 'react'
import type { CartProduct } from '../models/CartProduct';
import { CartSumContext } from '../context/CartSumContext';
import { decrement, increment, decrementByAmount, reset } from '../store/counterSlice'
import { useAppDispatch } from '../store/store';
import { calculateCartSum } from '../util/calculations';

const backendUrl = import.meta.env.VITE_API_HOST;

function Cart() {

  const [cartProducts, setCartProducts] = useState<CartProduct[]>(JSON.parse(localStorage.getItem("cart") || "[]"));
  const {increaseSum, decreaseSum, resetSum} = useContext(CartSumContext);
  const dispatch = useAppDispatch();

  function deleteFromCart(index: number) {
    const updatedCart = [...cartProducts];
    const cartItem = updatedCart[index];
    decreaseSum(cartItem.product.price * cartItem.quantity)
    updatedCart.splice(index, 1); // esimene nr on mitmendat, teine nr on mitu tk kustutan alates indexist
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    //setCartProducts(cartProducts.slice());
    setCartProducts(updatedCart);
    //setCartProducts(JSON.parse(localStorage.getItem("cart") || "[]"));
    dispatch(decrementByAmount(cartItem.quantity))
  }

  function emptyCart() {
    setCartProducts([]);
    //localStorage.setItem("cart", "[]");   /// VÕI NII NAGU ALL
    localStorage.removeItem("cart");
    resetSum();
    dispatch(reset());
  }
  
  function decreaseQuantity(index: number) {
    const updatedCart = [...cartProducts];
    const cartItem = updatedCart[index];
    if (cartItem.quantity === 1) {
      deleteFromCart(index);
      return;
    }
    cartItem.quantity--;
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    setCartProducts(updatedCart);
    decreaseSum(cartItem.product.price);
    dispatch(decrement());
  }

  function increaseQuantity(index: number) {
    const updatedCart = [...cartProducts];
    const cartItem = updatedCart[index];
    cartItem.quantity++;
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    setCartProducts(updatedCart);
    increaseSum(cartItem.product.price);
    dispatch(increment());
  }

  // function calculateCartSum() {
  //   let sum = 0;
  //   cartProducts.forEach(cp => sum += cp.product.price * cp.quantity);
  //   return sum;
  // }


  function order() {
    fetch(`${backendUrl}/orders`, {
      method: "POST",
      body: JSON.stringify(cartProducts),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      }
    })
      .then(res => res.json())
      .then(json => window.location.href = json.link)
  }
  // URLi muutmiseks:
  // HTML
  // Reacti siseselt: <Link to="/ostukorv">
  // Reacit v2liselt: <a href="instagram.com/username">

  // JavaScript
  // Reacti siseselt: useNavigate navigate("/admin")
  // Reacti v2liselt: window.location.href = "https://everypay.com"

  return (
    <div>
      {cartProducts.length > 0 ?
      <>
        <button onClick={emptyCart}>Tühjenda</button>
        {cartProducts.map( (cp, index) => 
          <div key={index}>
            <span>{cp.product.name}</span>
            <span>{cp.product.price.toFixed(2)}</span>
            <button disabled={cp.quantity === 1} onClick={() => decreaseQuantity(index)}>-</button>
            <span>{cp.quantity} tk</span>
            <button onClick={() => increaseQuantity(index)}>+</button>
            <span>{(cp.product.price * cp.quantity).toFixed(2)}€</span>
            <span><button onClick={() => deleteFromCart(index)}>X</button></span>
          </div>
        )}

        <div>Kogusumma: {calculateCartSum(cartProducts).toFixed(2)}</div>
        <button onClick={order}>Telli</button>
      </> : 
      <>
        <div>Ostukorv on tühi</div>
      </>}
      
    </div>
  )
}

export default Cart
