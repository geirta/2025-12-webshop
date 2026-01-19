import { useState, type ReactNode } from "react";
import { CartSumContext } from "./CartSumContext";
import type { CartProduct } from "../models/CartProduct";
import { calculateCartSum } from "../util/calculations";

// provider abil määran, milline component saab kätte
export const CartSumContextProvider = ({children}: {children: ReactNode}) => {
  
  const [sum, setSum] = useState(getCartSum());

  function decreaseSum(amount: number) {
    setSum(sum - amount);
  }

  function increaseSum(amount: number) {
    setSum(sum + amount);
  }

  function resetSum() {
    setSum(0);
  }

  function getCartSum() {
    const cart: CartProduct[] = JSON.parse(localStorage.getItem("cart") || "[]");
    // let sum = 0;
    // cart.forEach(cp => sum += cp.product.price * cp.quantity);
    // return sum;
    return calculateCartSum(cart);
  }

  return (
    <CartSumContext.Provider value={{sum, decreaseSum, increaseSum, resetSum}}>
      {children}
    </CartSumContext.Provider>
  )
}